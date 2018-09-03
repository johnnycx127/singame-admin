package com.singame.admin.query.filter;

import lombok.Data;
import lombok.Builder;
import lombok.ToString;

@Data
@Builder
@ToString
public class RoleFilter {
  private String name;
  private String namePartten;
  private Long userId;
  private Long permissionId;
}