package com.singame.admin.vo;

import lombok.AllArgsConstructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.singame.admin.utils.EnumUtil;

@AllArgsConstructor
public enum Gender {
  UNKNOW(0),
  MALE(1),
  FEMALE(2);

  private final int value;

  public int value() {
    return this.value;
  }

  @JsonCreator
  public static Gender forValue(String value) {
    return EnumUtil.getEnumFromString(Gender.class, value);
  }

  @JsonValue
  public String toString() {
    return name().toUpperCase();
  }
}