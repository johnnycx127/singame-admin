package com.singame.admin.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(Include.NON_EMPTY)
public class Reply <T> {
  // 响应业务状态码
  private Integer code;
  // 响应信息
  private String msg;
  // payload
  private T payload = null;

  public Reply(Integer code, String msg) {
    this(code, msg, null);
  }

  public Reply(Integer code, String msg, T payload) {
    this.code = code;
    this.msg = msg;
    this.payload = payload;
  }
}