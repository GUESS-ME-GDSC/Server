package gdsc.mju.guessme.domain.quiz;

import com.google.cloud.vision.v1.*;
import gdsc.mju.guessme.domain.info.dto.InfoObj;
import gdsc.mju.guessme.domain.info.repository.InfoRepository;
import gdsc.mju.guessme.domain.person.entity.Person;
import gdsc.mju.guessme.domain.person.repository.PersonRepository;
import gdsc.mju.guessme.domain.quiz.dto.NewScoreDto;
import gdsc.mju.guessme.domain.quiz.dto.QuizDto;
import gdsc.mju.guessme.domain.quiz.dto.QuizResDto;
import gdsc.mju.guessme.domain.quiz.dto.ScoreReqDto;
import gdsc.mju.guessme.domain.quiz.entity.Scoring;
import gdsc.mju.guessme.domain.quiz.repository.ScoringRepository;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.infra.gcs.GcsService;
import gdsc.mju.guessme.global.response.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
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
    public Boolean scoring(String username, ScoreReqDto dto) throws IOException {

        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        String infoValue = dto.getInfoValue(); // 클라에서 갖고 있는 정답
        Long infoId = dto.getInfoId();

        String imageUrl = dto.getImage() != null ?
                gcsService.uploadFile(dto.getImage()) : null;

        String textFromImage = detectText(imageUrl); // 이미지 추출 텍스트

        // 한글로만 입력 받는다고 가정, 두 글자 이상 연속으로 중복되는 경우 정답 처리
        // 이 코드는 gpt로 변경
        String overlap = findOverlap(infoValue, textFromImage);
        // 채점 후 삭제하기
        String fileUUID = imageUrl.split("/")[4];
        gcsService.deleteFile(fileUUID);

        Scoring scoring = scoringRepository.findByInfoId(infoId);

        if (overlap != null) { // 맞았을 경우
            // 테이블에 있는지 조회
            if (scoringRepository.existsByInfoId(infoId)) {
                scoring.setWrongFlag(0L);
            } else {
                Person person = personRepository.findById(dto.getPersonId())
                        .orElseThrow();

                // 없으면 테이블 삽입
                scoringRepository.save(Scoring.builder()
                        .infoId(infoId)
                        .wrongFlag(0L)
                        .person(person)
                        .build());
            }
            return Boolean.TRUE;
        } else { // 틀렸을 경우
            // 테이블에 있는지 조회
            if (scoringRepository.existsByInfoId(infoId)) {
                // 있으면 flag++
                Long flag = scoring.getWrongFlag() + 1;
                // if flag == 3 -> 행 지우고 메일 보내기
                if (flag == 3) {
                    scoringRepository.deleteByInfoId(infoId);
                    // 메일 보내기
                    executorService.submit(new Runnable() {

                        @Override
                        public void run() {
                            MimeMessage message = javaMailSender.createMimeMessage();
                            try {
//                                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

                                // 1. 메일 제목 설정
                                message.setSubject("Guess me! : check on your loved one!");


                                // 2. 메일 수신자 설정
                                String receiver = user.getEmail();
//                                messageHelper.setTo(receiver);
                                message.addRecipients(MimeMessage.RecipientType.TO, receiver);

                                // 3. 메일 내용 설정
//                                messageHelper.setText("The quiz score is too low recently, we recommend you to check on your loved one.");
                                Context context = new Context();
                                message.setText(templateEngine.process("mail", context), "utf-8", "html");
//                                messageHelper.addInline("image1", new ClassPathResource("templates/family.jpg"));


                                // 4. 메일 전송
                                javaMailSender.send(message);
                            } catch (Exception e) {
                                System.out.println("error : " + e.toString());
                            }
                        }
                    });
                } else {
                    scoring.setWrongFlag(flag);
                }

            } else {
                // 없으면 continue
            }

            return Boolean.FALSE;
        }
    }


    public String findOverlap(String str1, String str2) {
        // 더 긴 문자열 str1로 설정
        if (str1.length() < str2.length()) {
            String temp = str1;
            str1 = str2;
            str2 = temp;
        }

        // 더 긴 문자열을 기준으로 루프를 돌면서 서브스트링을 만들어 비교
        for (int i = str2.length(); i > 0; i--) {
            for (int j = 0; j <= str2.length() - i; j++) {
                if (str1.contains(str2.substring(j, j + i))) {
                    String ans = str2.substring(j, j + i);
                    if (ans.length() >= 2) {
                        return ans;
                    }
                    return null;
                }
            }
        }

        return null; // 겹치는 단어가 없을 경우 null 반환
    }
}