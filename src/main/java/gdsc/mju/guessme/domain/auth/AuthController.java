package gdsc.mju.guessme.domain.auth;

import gdsc.mju.guessme.domain.auth.jwt.JwtTokenProvider;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .userId(user.get("userId"))
                .userPassword(passwordEncoder.encode(user.get("userPassword")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUserId(user.get("userId"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 id 입니다."));

        if (!passwordEncoder.matches(user.get("userPassword"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}