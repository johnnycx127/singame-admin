package com.singame.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.singame.admin.domain.Role;

import org.joda.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value="RoleDTO", description="角色")
public class RoleDTO {
  private Long id;
  @ApiModelProperty(value="名称")
  private String name;
  @ApiModelProperty(value="创建人")
  private Long createdBy;
  @ApiModelProperty(value="修改人")
  private Long updatedBy;
  @ApiModelProperty(value="删除人")
  private Long removedBy;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  @ApiModelProperty(example="2017-01-01 00:00:00")
  private LocalDateTime createdAt;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  @ApiModelProperty(example="2017-01-01 00:00:00")
  private LocalDateTime updatedAt;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  @ApiModelProperty(example="2017-01-01 00:00:00")
  private LocalDateTime removedAt;
  @ApiModelProperty(value="版本信息")
  private Integer version;
  @ApiModelProperty(value="分配权限版本")
  private Integer dispatchPermissionVersion;

  public Role toConvertEntity() {
    Role role = new Role();
    role.setId(id);
    role.setName(name);
    role.setCreatedBy(createdBy);
    role.setCreatedAt(createdAt);
    role.setUpdatedBy(updatedBy);
    role.setUpdatedAt(updatedAt);
    role.setRemovedBy(removedBy);
    role.setRemovedAt(removedAt);
    role.setVersion(version);
    role.setDispatchPermissionVersion(dispatchPermissionVersion);
    return role;
  }
}