package com.singame.admin.exception;

import com.singame.admin.common.Reply;
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
  public final ResponseEntity<Reply<?>> handleDataConflictExceptions(DataConflictException ex, WebRequest request) {
    Reply<?> exceptionResponse = new Reply<>(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<Reply<?>> handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
    Reply<?> exceptionResponse = new Reply<>(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<Reply<?>> handleBadReqExceptions(BadRequestException ex, WebRequest request) {
    Reply<?> exceptionResponse = new Reply<>(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ApiException.class)
  public final ResponseEntity<Reply<?>> handleApiExceptions(ApiException ex, WebRequest request) {
    Reply<?> exceptionResponse = new Reply<>(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Reply<?>> handleAllExceptions(Exception ex, WebRequest request) {
    Reply<?> exceptionResponse = new Reply<>(ReplyBizStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}