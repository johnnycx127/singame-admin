package com.singame.admin.exception;

import com.singame.admin.common.ReplyBizStatus;

import lombok.Getter;

@Getter
public class BadRequestException extends ApiException {
  private static final long serialVersionUID = -1869863880901751260L;

  public BadRequestException() {
    this("Bad Request!");
  }

  public BadRequestException(String msg) {
    super(ReplyBizStatus.BAD_REQUEST, msg);
  }
}