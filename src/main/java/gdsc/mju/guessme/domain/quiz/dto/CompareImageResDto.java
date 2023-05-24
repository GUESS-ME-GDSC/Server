package gdsc.mju.guessme.domain.quiz.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CompareImageResDto {
    private Double similarity;

    @Builder
    public CompareImageResDto(Double similarity) {
        this.similarity = similarity;
    }
}