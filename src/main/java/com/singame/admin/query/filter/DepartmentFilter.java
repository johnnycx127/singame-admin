package com.singame.admin.query.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class DepartmentFilter {
  private String name;
  private String namePartten;
}