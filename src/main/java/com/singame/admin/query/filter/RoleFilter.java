package com.singame.admin.query.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class RoleFilter {
  private String name;
  private Long userId;
  private Long permissionId;
}