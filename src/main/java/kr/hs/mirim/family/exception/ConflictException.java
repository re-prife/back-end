package kr.hs.mirim.family.exception;

import kr.hs.mirim.family.exception.handler.ApiException;
import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException {
    private static final int status = HttpStatus.CONFLICT.value();

    public ConflictException(String message) {
        super(status, message);

    }
}
