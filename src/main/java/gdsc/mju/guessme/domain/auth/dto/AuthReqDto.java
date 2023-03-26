package gdsc.mju.guessme.domain.auth.dto;

import gdsc.mju.guessme.domain.user.entity.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthReqDto {
    private String userId;
    private String userPassword;

    public AuthReqDto(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public User toUserEntity() {
        return User.builder()
            .userId(userId)
            .userPassword(userPassword)
            .build();
    }
}
