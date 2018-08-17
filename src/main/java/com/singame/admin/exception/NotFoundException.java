package com.singame.admin.exception;

import com.singame.admin.common.ReplyBizStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends ApiException {
  private static final long serialVersionUID = 7784303438684941314L;

  public NotFoundException() {
    this("api url not found!");
  }
  public NotFoundException(String msg) {
    super(ReplyBizStatus.NOT_FOUND, msg);
  }
}