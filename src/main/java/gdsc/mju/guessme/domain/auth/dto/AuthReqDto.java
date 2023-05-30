package gdsc.mju.guessme.domain.auth.dto;

import gdsc.mju.guessme.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AuthReqDto {
    private String userId;
    private String userPassword;
    private String email;

    public AuthReqDto(String userId, String userPassword, String email) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
    }

    public User toUserEntity() {
        return User.builder()
                .userId(userId)
                .userPassword(userPassword)
                .email(email)
                .build();
    }
}
