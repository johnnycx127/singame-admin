package com.singame.admin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class ApiExceptionResponse {
  private String message;
  @NonNull private int code;
}