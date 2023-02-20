package com.example.test.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, ApiException e) {
        log.error("ApiException URI : " + request.getRequestURI() + " [ " + e.getLocalizedMessage() + " ]");
        ErrorResponse errorResponse = ErrorResponse.builder().msg(e.getLocalizedMessage()).status(e.getHttpStatus()).build();
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handlerGlobalException(HttpServletRequest request, MethodArgumentNotValidException e) {
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("] (은)는");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }
        log.error("validException URI : " + request.getRequestURI() + " [ " + e.getLocalizedMessage() + " ] ");
        ErrorResponse errorResponse = ErrorResponse.builder().msg(builder.toString()).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<ErrorResponse> handlerGlobalException(HttpServletRequest request, BindException e) {
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getCode());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        log.error("ValidException URI : " + request.getRequestURI() + " [ " + e.getLocalizedMessage() + " ] ");
        ErrorResponse errorResponse = ErrorResponse.builder().msg(builder.toString()).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { HttpMessageConversionException.class })
    public ResponseEntity<ErrorResponse> handlerGlobalException(HttpServletRequest request, HttpMessageConversionException e) {
        log.error("Exception URI : " + request.getRequestURI() + " [ " + e.getLocalizedMessage() + " ] ");
        String errorMsg = "적절한 값이 사용되지 않았습니다 다시 확인해주세요.";
        ErrorResponse errorResponse = ErrorResponse.builder().msg(errorMsg).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, NoSuchElementException e) {
        log.error("Exception URI : " + request.getRequestURI() + " [ " + e.getLocalizedMessage() + " ] ");
        String errorMsg = "존재하지 않는 객체입니다 id를 다시 확인해주세요.";
        ErrorResponse errorResponse = ErrorResponse.builder().msg(errorMsg).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception e) {
        log.error("Exception URI : " + request.getRequestURI() + " [ " + e.getLocalizedMessage() + " ] ");
        ErrorResponse errorResponse = ErrorResponse.builder().msg(e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Builder

    private static class ErrorResponse {
        private final HttpStatus status;

        private final String msg;
    }
}