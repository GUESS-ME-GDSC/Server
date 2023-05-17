package gdsc.mju.guessme.domain.quiz;


import gdsc.mju.guessme.global.response.BaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizServiceTest {
    @Autowired
    private QuizService quizService;

    @Test
    void scoringMethodForTest() throws BaseException, IOException {
        String username = "hou27";
        String infoKey = "name";
        String infoValue = "newname";
        Long personId = 1L;
        String textFromImage = "newname";

        Boolean result = quizService.scoringMethodForTest(username, infoKey, infoValue, personId, textFromImage);

        assertEquals(true, result);
    }

    @Test
    void sendEmailForTest() {
        quizService.sendEmailForTest("ataj125@gmail.com");
    }
}