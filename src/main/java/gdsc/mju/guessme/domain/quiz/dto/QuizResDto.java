package gdsc.mju.guessme.domain.quiz.dto;

import lombok.*;

import java.util.List;

@Data
@Getter @Setter
@NoArgsConstructor
public class QuizResDto {
    private String image;
    private String voice;
    private Long score;
    private List<QuizDto> quizList;

    @Builder
    public QuizResDto(String image, String voice, Long score, List<QuizDto> quizList) {
        this.image = image;
        this.voice = voice;
        this.score = score;
        this.quizList = quizList;
    }
}
