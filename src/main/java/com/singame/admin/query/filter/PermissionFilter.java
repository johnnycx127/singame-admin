package com.singame.admin.query.filter;

import com.singame.admin.vo.PermissionAction;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class PermissionFilter {
  private String code;
  private String codePartten;
  private String name;
  private String namePartten;
  private String resource;
  private String resourcePrefix;
  private Long roleId;
  private PermissionAction action;
}