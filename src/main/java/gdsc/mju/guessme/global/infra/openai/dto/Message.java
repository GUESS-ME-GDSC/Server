package gdsc.mju.guessme.global.infra.openai.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
    private String role;
    private String content;

    public Message(String content) {
        this.role = "user";
        this.content = content;
    }
}
