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
@ApiModel(value="DispatchUserRoleDTO", description="为用户分配权限的请求")
public class DispatchUserRoleDTO {
  @ApiModelProperty(value="用户ID", required=true)
  private Long userId;
  @ApiModelProperty(value="角色ID列表, 如果为空列表则将清空所有用户角色", required=true)
  private List<Long> roleIdList;
  @ApiModelProperty(value="版本信息")
  private Integer version;
}