package gdsc.mju.guessme.domain.quiz;

import gdsc.mju.guessme.domain.quiz.dto.NewScoreDto;
import gdsc.mju.guessme.domain.quiz.dto.QuizDto;
import gdsc.mju.guessme.domain.quiz.dto.QuizResDto;
import gdsc.mju.guessme.domain.quiz.dto.ScoreReqDto;
import gdsc.mju.guessme.global.response.BaseException;
import gdsc.mju.guessme.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/quiz")
public class QuizController {
    private final QuizService quizService;

    // 문제 전부 생성
    // id 안넣으면 해당 유저의 랜덤 인물에 대한 출력

    @GetMapping("/create/{personId}")
    public BaseResponse<QuizResDto> createQuiz(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable long personId
    ) throws BaseException {
        String username = userDetails.getUsername();
        return new BaseResponse<>(
                200,
                "Load Successfully",
                quizService.createQuiz(username, personId)
        );
    }

    // 새 점수 등록
    @PatchMapping("/newscore")
    public BaseResponse<Long> newscore(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestBody NewScoreDto newScoreDto
    ) throws BaseException {
        return new BaseResponse<>(
                200,
                "Load Successfully",
                quizService.newscore(newScoreDto)
        );
    }

    // 문제 채점

    // 클라이언트에서 문제 정답(인포 키), 이미지 주소를 넘겨주면 서버에서 맞는지 여부만 알려줌.
    // 클라이언트는 이미 인물 사진, 인물 이름, 문제 정답 가지고 있으므로 서버는 정답 여부만 Boolean으로 리턴해주면 됨.
    @PostMapping(value = "/scoring")
    // @requestbody 사용 시 formdata 오류. multidataForm 으로 넣으려면 어노테이션 빼야 함.
    public BaseResponse<Boolean> scoring(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute ScoreReqDto dto // requestBody 대신 @ModelAttribute??
    ) throws IOException {
        // dto에 인물id, 인포키, 파일 주소 넣어서 전달
        return new BaseResponse<>(
                200,
                "Load Successfully",
                quizService.scoring(dto)
        );
    }
}