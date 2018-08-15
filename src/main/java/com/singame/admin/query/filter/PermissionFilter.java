package com.singame.admin.query.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class PermissionFilter {
  private String name;
  private String resource;
  private Long roleId;
}