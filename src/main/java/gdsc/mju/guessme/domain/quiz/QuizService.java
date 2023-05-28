package gdsc.mju.guessme.domain.quiz;

import com.google.cloud.vision.v1.*;
import gdsc.mju.guessme.domain.info.dto.InfoObj;
import gdsc.mju.guessme.domain.info.repository.InfoRepository;
import gdsc.mju.guessme.domain.person.entity.Person;
import gdsc.mju.guessme.domain.person.repository.PersonRepository;
import gdsc.mju.guessme.domain.quiz.dto.*;
import gdsc.mju.guessme.domain.quiz.entity.Scoring;
import gdsc.mju.guessme.domain.quiz.repository.ScoringRepository;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.infra.gcs.GcsService;
import gdsc.mju.guessme.global.infra.openai.OpenAIService;
import gdsc.mju.guessme.global.response.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final InfoRepository infoRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final GcsService gcsService;
    private final OpenAIService openAIService;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final ScoringRepository scoringRepository;

    public QuizResDto createQuiz(String username, long personId) {

        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Person person;

        // personId 안주어졌을 때
        if (personId == 0) {
            List<Person> usersPersonList = personRepository.findByUser(user); // 해당 사용자의 모든 인물
            Collections.shuffle(usersPersonList); // 리스트 랜덤 셔플 후
            person = usersPersonList.get(0); // 0번째 원소 선택
        }

        // personId 주어졌을 때
        else {
            person = personRepository.findById(personId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인물입니다."));
        }

        // person 구했을 때 기본 정보 quizdto 에 넣기.
        List<QuizDto> quizDtoList = new ArrayList<>();

        QuizDto dto1 = new QuizDto();

        dto1.setQuestion("name");
        dto1.setAnswer(person.getName());

        quizDtoList.add(dto1);

        QuizDto dto2 = new QuizDto();

        dto2.setQuestion("relation");
        dto2.setAnswer(person.getRelation());

        quizDtoList.add(dto2);

        QuizDto dto3 = new QuizDto();

        dto3.setQuestion("birth");
        dto3.setAnswer(person.getBirth().toString());

        quizDtoList.add(dto3);

        QuizDto dto4 = new QuizDto();

        dto4.setQuestion("residence");
        dto4.setAnswer(person.getResidence());

        quizDtoList.add(dto4);

        // InfoObj dto로 변환
        List<InfoObj> infoObjList = InfoObj.of(infoRepository.findAllByPerson(person));


        // info는 따로 빼서 for 문 돌리기

        for (InfoObj elem : infoObjList) {
            QuizDto dto = new QuizDto();
            dto.setQuestion(elem.getInfoKey());
            dto.setAnswer(elem.getInfoValue());
            quizDtoList.add(dto);
        }

        return QuizResDto.builder()
                .personId(person.getId())
                .image(person.getImage())
                .voice(person.getVoice())
                .score(person.getScore())
                .quizList(quizDtoList)
                .build();
    }

    // 새 점수 등록
    public Long newscore(
            UserDetails userDetails,
            NewScoreDto newScoreDto
    ) throws BaseException {
        // load user
        User user = userRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // personId가 해당 user의 personList에 있는지 확인
        Long validPersonId = user.getPersonList().stream()
                .filter(person -> person.getId().equals(newScoreDto.getPersonId()))
                .findFirst()
                .orElseThrow(() -> new BaseException(403, "Your not allowed to access this person")).getId();

        personRepository.updateScore(validPersonId, newScoreDto.getScore());
        return newScoreDto.getScore();
    }

    // 원격 이미지에서 텍스트 추출
    // Detects text in the specified remote image on Google Cloud Storage.
    // https://cloud.google.com/vision/docs/ocr?hl=ko
    public String detectText(String gcsPath) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setImageUri(gcsPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    return null;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    return annotation.getDescription();
                }
            }
            return null;
        }
    }

    // 문제 채점 / 메일 전송
    // 이미지 포함한 메일
    // 메일 보내는거 오래 걸려서 @Async 적용
    // 점수 저장은 잘 되는데 반환값에 null 나옴. 왜?
    // 비동기처리 안하면 엄청 느림
    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Transactional
    public Boolean scoring(
            String username,
            ScoreReqDto dto
    ) throws IOException, BaseException {

        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new BaseException(404, "User not found"));

        String infoKey = dto.getInfoKey(); // 문제
        String infoValue = dto.getInfoValue(); // 정답

        String imageUrl = dto.getImage() != null ?
                gcsService.uploadFile(dto.getImage()) : null;

        if (imageUrl == null) {
            throw new BaseException(400, "Can not upload image!");
        }
        String fileUUID = imageUrl.split("/")[4];

        // 여기서 텍스트 못읽을 시 에러 발생해야 함.
        String textFromImage = detectText(imageUrl); // 이미지 추출 텍스트

        if (textFromImage == null) {
            throw new BaseException(400, "Can not read text from image!");
        }

        // personId로 person 찾기
        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new BaseException(404, "Person not found"));

        // db에서 문제에 대한 채점 정보 가져오기
        Scoring scoring = scoringRepository.findByQuestionAndPerson(infoKey, person);

        // chat gpt를 통해 채점
        Boolean correct = scoringWithChatGpt(textFromImage, infoKey, infoValue);

        // 맞은 경우
        if (correct) {
            // 테이블에 있는지 조회
            if (scoring != null) {
                // 있으면 flag = 0으로 삽입
                scoring.setWrongFlag(0L);

                // 현재 이미지와 이전 이미지 유사도 검사
                String curFile = scoring.getAnswer();

                System.out.println("curFile = " + curFile);
                // ml server에 요청 ( image compare )
                CompareImageResDto response = compareImage(imageUrl, curFile);

                System.out.println("Response: " + response.getDissimilarity());
                double dissimilarity = response.getDissimilarity();
                if (dissimilarity > 2.00) {
                    // Dissimilarity가 2.0 이상이면 보호자에게 보고 메일 전송
//                        sendEmail();
                }
                // 유사도 검사까지 마무리 후 이미지 삭제
                gcsService.deleteFile(fileUUID);
            } else {
                // 없으면 새로 삽입
                scoringRepository.save(Scoring.builder()
                        .question(infoKey)
                        .wrongFlag(0L)
                        .person(person)
                        .answer(imageUrl)
                        .build());
            }

            return Boolean.TRUE;
        } else if (scoring != null) { // 테이블에 기존 채점 정보가 있는데 틀렸을 경우
            // 있으면 flag 증가
            long flag = scoring.getWrongFlag() + 1;
            // if flag == 3 -> 행 지우고 메일 보내기
            if (flag == 3) {
                scoringRepository.deleteByQuestionAndPerson(infoKey, person);
                // 메일 보내기
                sendEmail(user);
            } else {
                scoring.setWrongFlag(flag);
            }
        }

        // 틀린 경우 이미지 삭제
        gcsService.deleteFile(fileUUID);

        return Boolean.FALSE;
    }

    /**
     * ml server에 요청
     */
    public CompareImageResDto compareImage(String file1_url, String file2_url) {
        // query parameter 방식 사용
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://[ml_server_url]/compare_images")
                .queryParam("file1", file1_url)
                .queryParam("file2", file2_url)
                .build(false);

        // Header 제작
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // api 호출 타임아웃
        factory.setReadTimeout(5000); // api 읽기 타임아웃

        RestTemplate restTemplate = new RestTemplate(factory);

        // 응답
        return restTemplate.getForObject(uriComponents.toUriString(), CompareImageResDto.class);
    }

    /**
     * 메일 전송
     *
     * @param user
     */
    private void sendEmail(User user) {
        executorService.submit(() -> {
            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                // 1. 메일 제목 설정
                message.setSubject("Guess me! : check on your loved one!");
                // 2. 메일 수신자 설정
                message.addRecipients(MimeMessage.RecipientType.TO, user.getEmail());

                // 3. 메일 내용 설정
                Context context = new Context();
                message.setText(templateEngine.process("mail", context), "utf-8", "html");

                javaMailSender.send(message);
            } catch (Exception e) {
                throw new RuntimeException("Failed to send email", e);
            }
        });
    }

    /**
     * chat gpt를 통해 채점
     *
     * @param textFromImage
     * @param question
     * @param answer
     * @return true or false
     * @throws BaseException
     */
    private Boolean scoringWithChatGpt(String textFromImage, String question, String answer) throws BaseException {
        String prompt = String.format(
                "You are a scoring machine now. You can only say yes or no. Here is the situation. " +
                        "I asked an old man a question about guessing %s, and the correct answer is \"%s\"." +
                        "The format of the answer is free because the elderly with dementia are the submitters." +
                        "He submitted \"%s\". If it is correct, please say \"yes,\" otherwise say \"no.\"",
                question, answer, textFromImage
        );

        System.out.println("prompt = " + prompt);
        // return true or false
        return processResponse(this.openAIService.createCompletion(prompt));
    }

    private Boolean processResponse(String response) {
        // Check if the response contains "yes"
        System.out.println("response = " + response);

        // make response lowercase
        response = response.toLowerCase();

        if (response.contains("yes")) {
            return true;
        }

        // Check if the response contains "no"
        if (response.contains("no")) {
            return false;
        }

        // If neither "yes" nor "no" is found, return false as the default value
        return false;
    }
}