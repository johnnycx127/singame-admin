package com.singame.admin.query.filter;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class DepartmentFilter {
  private String name;
  private String namePartten;
}