package gdsc.mju.guessme.global.infra.openai;

import gdsc.mju.guessme.global.response.BaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAIServiceImplTest {
    @Autowired
    private OpenAIService openAIService;

    @Test
    void createCompletion() throws BaseException {
        String prompt = "Say Hi";
        String result = openAIService.createCompletion(prompt);
        System.out.println(result);

        // check if is existed
        assertNotNull(result);
    }
}