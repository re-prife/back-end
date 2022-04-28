package kr.hs.mirim.family.exception;

import kr.hs.mirim.family.exception.handler.ApiException;
import org.springframework.http.HttpStatus;

public class IncorrectUserAccountException extends ApiException{
    private static final int status = HttpStatus.BAD_REQUEST.value();

    public IncorrectUserAccountException(String message) {
        super(status, message);

    }
}
