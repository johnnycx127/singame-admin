package com.singame.admin.exception;

import com.singame.admin.common.ReplyBizStatus;

import lombok.Getter;

@Getter
public class InteralException extends ApiException {
  public InteralException() {
    this("Interal Service error");
  }

  public InteralException(String msg) {
    super(ReplyBizStatus.INTERNAL_SERVER_ERROR, msg);
  }
}