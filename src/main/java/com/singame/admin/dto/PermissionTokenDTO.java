package com.singame.admin.dto;

import java.util.List;

import com.singame.admin.domain.Role;
import com.singame.admin.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class PermissionTokenDTO {
  private User user;
  private List<Role> roles;
  private List<String> authurls;
}