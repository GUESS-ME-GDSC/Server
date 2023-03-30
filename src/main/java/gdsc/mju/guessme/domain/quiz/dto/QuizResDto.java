package gdsc.mju.guessme.domain.quiz.dto;

import lombok.*;

import java.util.List;

@Data
@Getter @Setter
@NoArgsConstructor
public class QuizResDto {
    private Long personId;
    private String image;
    private String voice;
    private Long score;
    private List<QuizDto> quizList;

    @Builder
    public QuizResDto(Long personId, String image, String voice, Long score, List<QuizDto> quizList) {
        this.personId = personId;
        this.image = image;
        this.voice = voice;
        this.score = score;
        this.quizList = quizList;
    }
}
