package com.singame.admin.exception;

import com.singame.admin.common.ReplyBizStatus;

import lombok.Getter;

@Getter
public class AccessForbiddenException extends ApiException {
  private static final long serialVersionUID = 7784303438684941314L;

  public AccessForbiddenException() {
    this("access forbidden");
  }
  public AccessForbiddenException(String msg) {
    super(ReplyBizStatus.ACCESS_FORBIDDEN, msg);
  }
}