package gdsc.mju.guessme.domain.auth;

import gdsc.mju.guessme.domain.auth.dto.AuthReqDto;
import gdsc.mju.guessme.global.response.BaseException;
import gdsc.mju.guessme.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public BaseResponse<Void> join(@RequestBody AuthReqDto joinReq) throws BaseException {
        authService.join(joinReq);
        return new BaseResponse<>(
            201,
            "Join Successfully",
            null
        );
    }

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody AuthReqDto loginReq) throws BaseException {
        String t = authService.login(loginReq);
        return new BaseResponse<>(
            201,
            "Login Successfully",
            t
        );
    }
}
