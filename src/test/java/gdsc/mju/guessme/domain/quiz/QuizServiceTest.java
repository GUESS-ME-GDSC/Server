package gdsc.mju.guessme.domain.quiz;


import gdsc.mju.guessme.domain.quiz.dto.CompareImageResDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizServiceTest {
    @Autowired
    private QuizService quizService;

    @Test
    void compareImage() {
        String file1 = "https://github.com/GUESS-ME-GDSC/ML/assets/65845941/cc5523f3-4d6c-4765-a5c8-aacbfc83d588";
        String file2 = "https://github.com/GUESS-ME-GDSC/ML/assets/65845941/3b601689-33b6-462b-8ca4-aa9c1b7dd975";

        CompareImageResDto response = quizService.compareImage(file1, file2);

        // assert response.getDissimilarity(); type is double and bigger than 0.0
        assertNotNull(response.getDissimilarity());
        assertTrue(response.getDissimilarity() > 0.0);
    }

//    @Test
//    void scoringMethodForTest() throws BaseException, IOException {
//        String username = "hou27";
//        String infoKey = "name";
//        String infoValue = "newname";
//        Long personId = 1L;
//        String textFromImage = "newname";
//
//        Boolean result = quizService.scoringMethodForTest(username, infoKey, infoValue, personId, textFromImage);
//
//        assertEquals(true, result);
//    }
//
//    @Test
//    void sendEmailForTest() {
//        quizService.sendEmailForTest("ataj125@gmail.com");
//    }
}