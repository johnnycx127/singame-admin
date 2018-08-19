package com.singame.admin.exception;

import com.singame.admin.common.ReplyBizStatus;

import lombok.Getter;

@Getter
public class DataConflictException extends ApiException {
  private static final long serialVersionUID = 7784303438684941314L;

  public DataConflictException() {
    this("data conflict");
  }

  public DataConflictException(String msg) {
    super(ReplyBizStatus.DATA_CONFLICT, msg);
  }
}