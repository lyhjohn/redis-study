package com.example.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ExceptionForm> runtimeHandler(HttpMediaTypeNotAcceptableException e) {
        System.out.println("handler");
        return ResponseEntity.badRequest().body(new ExceptionForm(e.getMessage()));
    }

    static class ExceptionForm {
        private String message;
        private HttpStatus status;

        public ExceptionForm(String message) {
            this.message = message;
            this.status = HttpStatus.BAD_REQUEST;
        }
    }
}
