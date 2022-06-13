package kr.hs.mirim.family.exception;

import kr.hs.mirim.family.exception.handler.ApiException;
import org.springframework.http.HttpStatus;

public class InternalServerException extends ApiException {
    private static final int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public InternalServerException(String message) {
        super(status, message);
    }
}