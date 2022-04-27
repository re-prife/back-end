package kr.hs.mirim.family.exception;

import kr.hs.mirim.family.exception.handler.ApiException;
import org.springframework.http.HttpStatus;

public class DataNotFoundException extends ApiException {
    private static final int status = HttpStatus.NOT_FOUND.value();

    public DataNotFoundException(String message) {
        super(status, message);
    }
}
