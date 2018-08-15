package com.singame.admin.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Sorter {
  private String fieldName;
  private String sort;
}