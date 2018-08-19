package com.singame.admin.exception;

import com.singame.admin.common.ResponseBody;
import com.singame.admin.common.ReplyBizStatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DataConflictException.class)
  public final ResponseEntity<ResponseBody<?>> handleDataConflictExceptions(DataConflictException ex, WebRequest request) {
    ResponseBody<?> exceptionResponse = new ResponseBody<>(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<ResponseBody<?>> handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
    ResponseBody<?> exceptionResponse = new ResponseBody<>(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ResponseBody<?>> handleBadReqExceptions(BadRequestException ex, WebRequest request) {
    ResponseBody<?> exceptionResponse = new ResponseBody<>(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ApiException.class)
  public final ResponseEntity<ResponseBody<?>> handleApiExceptions(ApiException ex, WebRequest request) {
    ResponseBody<?> exceptionResponse = new ResponseBody<>(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ResponseBody<?>> handleAllExceptions(Exception ex, WebRequest request) {
    ResponseBody<?> exceptionResponse = new ResponseBody<>(ReplyBizStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}