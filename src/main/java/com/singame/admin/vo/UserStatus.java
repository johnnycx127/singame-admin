package com.singame.admin.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.singame.admin.utils.EnumUtil;

@AllArgsConstructor
public enum UserStatus {
  UNKNOW(0),
  // 正常
  AVAILABLE(1),
  // 冻结
  FREEZE(2),
  // 不可用
  UNAVAILABLE(3);

  private final int value;

  public int value() {
    return this.value;
  }

  @JsonCreator
  public static UserStatus forValue(String value) {
    return EnumUtil.getEnumFromString(UserStatus.class, value);
  }

  @JsonValue
  public String toJson() {
    return name().toUpperCase();
  }
}