package kr.hs.mirim.family.exception;

import kr.hs.mirim.family.exception.handler.ApiException;
import org.springframework.http.HttpStatus;

public class DateOverException extends ApiException {
    private static final int status = HttpStatus.CONFLICT.value();

    public DateOverException(String message) {
        super(status, message);

    }
}
