package gdsc.mju.guessme.domain.quiz.dto;

import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
public class QuizDto {
    private String question;
    private String answer;

    @Builder
    public QuizDto(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
