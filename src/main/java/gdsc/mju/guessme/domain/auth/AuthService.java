package gdsc.mju.guessme.domain.auth;

import gdsc.mju.guessme.domain.auth.dto.AuthReqDto;
import gdsc.mju.guessme.domain.auth.jwt.JwtTokenProvider;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.global.response.BaseException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder bCryptPasswordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public Boolean join(AuthReqDto joinReq) throws BaseException {

    if(userRepository.findByUserId(joinReq.getUserId()).isPresent()) {
      throw new BaseException(400, "User already exists");
    }
    User newUser = joinReq.toUserEntity();
    newUser.hashPassword(bCryptPasswordEncoder);

    userRepository.save(newUser);

    return true;
  }

  public String login(AuthReqDto loginReq) throws BaseException {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginReq.getUserId(),
              loginReq.getUserPassword()
          )
      );

      return jwtTokenProvider.generateAccessToken(authentication);
    } catch (AuthenticationException e) {
      throw new BaseException(400, "Invalid credentials supplied");
    }
  }
}
