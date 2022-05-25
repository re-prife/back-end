package kr.hs.mirim.family.exception;

import kr.hs.mirim.family.exception.handler.ApiException;
import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends ApiException {
    private static final int status = HttpStatus.METHOD_NOT_ALLOWED.value();

    public MethodNotAllowedException(String message) {
        super(status, message);
    }
}
