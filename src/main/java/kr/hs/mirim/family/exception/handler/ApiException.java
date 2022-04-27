package kr.hs.mirim.family.exception.handler;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final int status;
    private final String message;

    protected ApiException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}