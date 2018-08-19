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
  private String name;
  private String resource;
  private Long roleId;
  private PermissionAction action;
}