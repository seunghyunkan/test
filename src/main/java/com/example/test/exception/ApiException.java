package com.example.test.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    HttpStatus httpStatus;

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(HttpStatus status, String msg) {
        super(msg);
        httpStatus = status;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
