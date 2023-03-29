package gdsc.mju.guessme.domain.quiz.dto;

import lombok.*;

import java.util.List;

@Data
@Getter @Setter
@NoArgsConstructor
public class QuizResDto {
    private String image;
    private String voice;
    private List<QuizDto> quizList;

    @Builder
    public QuizResDto(String image, String voice, List<QuizDto> quizList) {
        this.image = image;
        this.voice = voice;
        this.quizList = quizList;
    }
}
