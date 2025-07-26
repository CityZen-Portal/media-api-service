package com.example.image.controller;

import com.example.image.enumeration.ResponseStatus;
import com.example.image.exception.BadRequestException;
import com.example.image.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse> handleBadRequest(BadRequestException ex) {
        CommonResponse response = new CommonResponse();
        response.setStatus(ResponseStatus.ERROR);
        response.setMessage(ex.getMessage());
        response.setData(null);
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGenericException(Exception ex) {
        CommonResponse response = new CommonResponse();
        response.setStatus(ResponseStatus.ERROR);
        response.setMessage("Internal Server Error: " + ex.getMessage());
        response.setData(null);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
