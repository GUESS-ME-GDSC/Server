package gdsc.mju.guessme.domain.quiz;

import com.google.cloud.vision.v1.*;
import gdsc.mju.guessme.domain.info.dto.InfoObj;
import gdsc.mju.guessme.domain.info.repository.InfoRepository;
import gdsc.mju.guessme.domain.person.entity.Person;
import gdsc.mju.guessme.domain.person.repository.PersonRepository;
import gdsc.mju.guessme.domain.quiz.dto.CreateQuizResDto;
import gdsc.mju.guessme.domain.quiz.dto.NewScoreDto;
import gdsc.mju.guessme.domain.quiz.dto.ScoreReqDto;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.infra.gcs.GcsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final InfoRepository infoRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final GcsService gcsService;

    public CreateQuizResDto createQuiz(long personId) {

        // 1L 대신 해당 사용자 id 얻어서 사용
        User user = userRepository.findById(1L)
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

        // InfoObj dto로 변환
        List<InfoObj> infoObjList = InfoObj.of(infoRepository.findAllByPerson(person));

        return CreateQuizResDto.builder()
                .person(person)
                .info(infoObjList)
                .build();
    }

    // 새 점수 등록
    public Long newscore(NewScoreDto newScoreDto) {
        Person person = personRepository.findById(newScoreDto.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인물입니다."));

        personRepository.updateScore(newScoreDto.getPersonId(), newScoreDto.getScore());
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
                    System.out.format("Error: %s%n", res.getError().getMessage());
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

    public Boolean scoring(ScoreReqDto dto) throws IOException {

        String infoValue = dto.getInfoValue(); // 클라에서 갖고 있는 정답

        String imageUrl = dto.getImage() != null ?
                gcsService.uploadFile(dto.getImage()) : null;


        String textFromImage = detectText(imageUrl); // 이미지 추출 텍스트
        System.out.println("textFromImage = " + textFromImage);

        // 한글로만 입력 받는다고 가정, 두 글자 이상 연속으로 중복되는 경우 정답 처리
        String overlap = findOverlap(infoValue, textFromImage);

        if (overlap != null) {
            System.out.println("겹치는 단어: " + overlap);
            return Boolean.TRUE;
        } else {
            System.out.println("겹치는 단어가 없습니다.");
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