package kr.hs.mirim.family.exception;

import kr.hs.mirim.family.exception.handler.ApiException;
import org.springframework.http.HttpStatus;

public class FormValidateException extends ApiException {
    private static final int status = HttpStatus.BAD_REQUEST.value();

    public FormValidateException(String message) {
        super(status, message);

    }
}