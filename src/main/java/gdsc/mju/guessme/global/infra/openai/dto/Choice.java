package gdsc.mju.guessme.global.infra.openai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Choice {
    private Message message;
    private String text;
    private Integer index;
    private String logprobs;

    @JsonProperty("finish_reason")
    private String finishReason;

}