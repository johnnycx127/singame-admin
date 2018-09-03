package com.singame.admin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value="UserAuthResponse", description="用户权限resp")
public class UserAuthDTO {
  private UserDTO user;
  @Singular(value="roleList")
  private List<RoleDTO> roleList;
  @Singular(value="permissionList")
  private List<PermissionDTO> permissionList;
}