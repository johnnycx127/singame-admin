package com.singame.admin.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends ApiException {
  private static final long serialVersionUID = 7784303438684941314L;

  public UnauthorizedException() {
    this("Unauthorized");
  }
  public UnauthorizedException(String msg) {
    super(BizStatus.UNAUTHORIZED, msg);
  }
}