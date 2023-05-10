package gdsc.mju.guessme.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseException extends Exception {
    private int status;
    private String message;

    public BaseException(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
