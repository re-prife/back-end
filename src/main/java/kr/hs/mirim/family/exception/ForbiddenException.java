package kr.hs.mirim.family.exception;

import kr.hs.mirim.family.exception.handler.ApiException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApiException {
    private static final int status = HttpStatus.FORBIDDEN.value();

    public ForbiddenException(String message) {
        super(status, message);
    }
}
