package com.mk.mbanking.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.mk.mbanking.base.ErrorApi;

@RestControllerAdvice
public class FileException {
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ErrorApi<?> handleMaxUploadSize(MaxUploadSizeExceededException e){
       return ErrorApi.builder()
       .status(false)
       .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
       .message("File size exceeds the maximum limit!")
       .timestamp(LocalDateTime.now())
       .errors(e.getMessage())
       .build();
    }
}
