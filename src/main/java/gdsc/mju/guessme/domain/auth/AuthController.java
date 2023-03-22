package gdsc.mju.guessme.domain.auth;

import gdsc.mju.guessme.domain.auth.jwt.JwtTokenProvider;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

// https://webfirewood.tistory.com/115

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/join")
    public BaseResponse<Long> join(@RequestBody Map<String, String> user) {
        return new BaseResponse<>(
                200,
                "Load Successfully",
                userRepository.save(User.builder()
                        .userId(user.get("userId"))
                        .userPassword(passwordEncoder.encode(user.get("userPassword")))
                        .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                        .build()).getId()
        );
    }

    // 로그인
    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUserId(user.get("userId"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 id 입니다."));

        if (!passwordEncoder.matches(user.get("userPassword"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return new BaseResponse<>(
                200,
                "Load Successfully",
                jwtTokenProvider.createToken(member.getUsername(), member.getRoles())
        );
    }

    /////////////////////

    // 토큰으로 유저 정보 얻기
    @GetMapping("/test/{token}")
    public void test(@PathVariable("token") String token) {
        System.out.println("erer");
        System.out.println(jwtTokenProvider.getUserPk(token));
    }

    // 헤더에서 토큰 가져오기
    @GetMapping("/test2")
    public void test2(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        System.out.println("token = " + token);

        String userId = jwtTokenProvider.getUserPk(token);
        System.out.println("userId = " + userId);
    }
}