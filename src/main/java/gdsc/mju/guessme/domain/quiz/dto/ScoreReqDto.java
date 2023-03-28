package gdsc.mju.guessme.domain.quiz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ScoreReqDto {
    private String infoValue;
    private String answer; // 사진
    private MultipartFile image;
}