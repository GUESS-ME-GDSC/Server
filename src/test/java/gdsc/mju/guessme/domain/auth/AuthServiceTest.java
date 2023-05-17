package gdsc.mju.guessme.domain.auth;

import static org.junit.jupiter.api.Assertions.*;

import gdsc.mju.guessme.domain.auth.dto.AuthReqDto;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.response.BaseException;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws BaseException {
        if(userRepository.findByUserId("AuthServiceTest").isEmpty()) {
            AuthReqDto joinReq = new AuthReqDto("AuthServiceTest", "test", "testEmail@email.com");
            authService.join(joinReq);
        }
    }

    @Test
    @Transactional
    void join() throws BaseException {
        // given, when

        // then
        userRepository.findByUserId("AuthServiceTest").ifPresent(user -> {
            assertEquals("AuthServiceTest", user.getUserId());
        });
    }

    @Test
    @Transactional
    void login() throws BaseException {
        // given
        AuthReqDto loginReq = new AuthReqDto("AuthServiceTest", "test", "testEmail");

        // when
        String result = authService.login(loginReq);

        // then
        assertNotNull(result);
    }
}