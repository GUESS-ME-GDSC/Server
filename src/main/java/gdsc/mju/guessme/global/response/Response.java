package gdsc.mju.guessme.global.response;

import lombok.Getter;

@Getter
public class Response<T> {
  private int status;
  private String message;

  private T data;

    public Response(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
