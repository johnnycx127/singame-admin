package com.singame.admin.utils;

public class EnumUtil {
  public static <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String value) {
    if (enumClass == null) {
      throw new IllegalArgumentException("enumClass value can not be null.");
    }
    for (Enum<?> enumValue : enumClass.getEnumConstants()) {
      if (enumValue.toString().equalsIgnoreCase(value)) {
        return (T) enumValue;
      }
    }

    StringBuilder errorMessage = new StringBuilder();
    boolean bFirstTime = true;
    for (Enum<?> enumValue : enumClass.getEnumConstants()) {
      errorMessage.append(bFirstTime ? "" : ",").append(enumValue);
      bFirstTime = false;
    }
    throw new IllegalArgumentException(value + " is invalid value, supported values are " + errorMessage);
  }
}