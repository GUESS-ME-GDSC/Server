package gdsc.mju.guessme.global.response;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public BaseResponse<String> handleException(Exception e) {
        return new BaseResponse<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            e.getMessage()
        );
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse<String> handleBaseException(BaseException e) {
        return new BaseResponse<>(
            e.getStatus(),
            e.getMessage(),
            null
        );
    }

    @ExceptionHandler({
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class,
        IllegalArgumentException.class
    })
    public BaseResponse<String> handleIllegalArgumentException(Exception e) {
        System.out.println("e = " + e.toString());
        return new BaseResponse<>(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            e.getMessage()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public BaseResponse<String> handleNotFoundException(NotFoundException e) {
        return new BaseResponse<>(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            e.getMessage()
        );
    }
}