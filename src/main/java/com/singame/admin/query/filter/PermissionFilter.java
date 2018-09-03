package com.singame.admin.query.filter;

import com.singame.admin.vo.PermissionAction;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
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