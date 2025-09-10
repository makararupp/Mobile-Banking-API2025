package com.mk.mbanking.exception;

import com.mk.mbanking.base.ErrorApi;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class MainException {
    @ExceptionHandler(Exception.class)
    public ErrorApi<?> handleMainException(Exception e) {
        return ErrorApi.builder()
                .status(false)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Something went wrong, please check in error detail!")
                .timestamp(LocalDateTime.now())
                .errors(e.getCause().getMessage())
                .build();
    }
}
