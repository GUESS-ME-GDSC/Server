package gdsc.mju.guessme.domain.quiz.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CompareImageResDto {
    private Double dissimilarity;

    @Builder
    public CompareImageResDto(Double dissimilarity) {
        this.dissimilarity = dissimilarity;
    }
}