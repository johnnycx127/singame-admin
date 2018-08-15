package com.singame.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ApiExceptionResponse> handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
        ApiExceptionResponse exceptionResponse = new ApiExceptionResponse(ex.getMessage(), ex.getCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ApiExceptionResponse> handleBadReqExceptions(BadRequestException ex, WebRequest request) {
        ApiExceptionResponse exceptionResponse = new ApiExceptionResponse(ex.getMessage(), ex.getCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ApiExceptionResponse> handleApiExceptions(ApiException ex, WebRequest request) {
        ApiExceptionResponse exceptionResponse = new ApiExceptionResponse(ex.getMessage(), ex.getCode());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ApiExceptionResponse exceptionResponse = new ApiExceptionResponse(ex.getMessage(),
                BizStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}