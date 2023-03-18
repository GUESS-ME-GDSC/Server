package gdsc.mju.guessme.domain.quiz;

import gdsc.mju.guessme.domain.quiz.dto.CreateQuizResDto;
import gdsc.mju.guessme.domain.quiz.dto.NewScoreDto;
import gdsc.mju.guessme.global.response.BaseException;
import gdsc.mju.guessme.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/quiz")
public class QuizController {
    private final QuizService quizService;

    // 문제 전부 생성 (특정 인물 info 모두 가져오기)
    // id 안넣으면 해당 유저의 랜덤 인물에 대한 출력
    // 우선 1번 유저 로그인 했다고 가정
    @GetMapping("/create/{personId}")
    public BaseResponse<CreateQuizResDto> createQuiz(@PathVariable long personId) throws BaseException {
        return new BaseResponse<>(
                200,
                "Load Successfully",
                quizService.createQuiz(personId)
        );
    }

    // 새 점수 등록
    @PatchMapping("/newscore")
    public BaseResponse<Long> newscore(@RequestBody NewScoreDto newScoreDto) throws BaseException {
        return new BaseResponse<>(
                200,
                "Load Successfully",
                quizService.newscore(newScoreDto)
        );
    }
}