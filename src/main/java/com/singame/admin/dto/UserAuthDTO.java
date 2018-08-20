package com.singame.admin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value="UserAuthResponse", description="用户权限resp")
public class UserAuthDTO {
  private UserDTO user;
  private List<RoleDTO> roleList;
  private List<PermissionDTO> permissionList;
}