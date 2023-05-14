package gdsc.mju.guessme.global.infra.openai;

import gdsc.mju.guessme.global.infra.openai.dto.ChatResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAIServiceImplTest {
    @Autowired
    private OpenAIService openAIService;

    @Test
    void createCompletion() {
        String prompt = "Say Hi";
        ChatResponse result = openAIService.createCompletion(prompt);
        System.out.println(result);

        // check if is existed
        assertNotNull(result.getChoices().get(0).getMessage().getContent());
    }
}