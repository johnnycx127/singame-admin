package com.singame.admin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value="DispatchRolePermissionDTO", description="为角色分配权限的请求")
public class DispatchRolePermissionDTO {
  @ApiModelProperty(value="角色ID", required=true)
  private Long roleId;
  @ApiModelProperty(value="权限ID列表, 如果是空列表则清空角色权限", required=true)
  private List<Long> permissionIdList;
  @ApiModelProperty(value="版本信息")
  private Integer version;
}