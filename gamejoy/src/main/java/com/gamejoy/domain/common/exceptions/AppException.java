package com.gamejoy.domain.common.exceptions;

import org.springframework.http.HttpStatus;

// todo: not necessary - can be rmeoved in future
@Deprecated
public class AppException extends RuntimeException {
    private final HttpStatus httpStatus;
    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
