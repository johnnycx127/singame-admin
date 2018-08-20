package com.singame.admin.exception;

import com.singame.admin.common.ReplyBizStatus;

import lombok.Getter;

@Getter
public class InternalServerException extends ApiException {
  public InternalServerException() {
    this("interal exception");
  }

  public InternalServerException(String msg) {
    super(ReplyBizStatus.INTERNAL_SERVER_ERROR, msg);
  }
}