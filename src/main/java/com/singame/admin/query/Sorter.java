package com.singame.admin.query;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Sorter {
  private String fieldName;
  private String sort;
}