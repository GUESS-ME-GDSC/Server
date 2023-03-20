package gdsc.mju.guessme.domain.quiz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScoreReqDto {
    private String infoValue;
    private String answer; // 사진
    private String filePath; // 이미지 파일 경로
}