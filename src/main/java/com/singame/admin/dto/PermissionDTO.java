package com.singame.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.singame.admin.domain.Permission;
import com.singame.admin.vo.PermissionAction;

import org.joda.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@Builder
@EqualsAndHashCode
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value="PermissionDTO", description="权限")
public class PermissionDTO {
  private Long id;
  @ApiModelProperty(value="编码")
  private String code;
  @ApiModelProperty(value="资源URL")
  private String resource;
  @ApiModelProperty(value="行为")
  private PermissionAction action;
  @ApiModelProperty(value="名称")
  private String name;
  @ApiModelProperty(value="描述")
  private String descritpion;
  @ApiModelProperty(value="创建者")
  private Long createdBy;
  @ApiModelProperty(value="是否管理员唯一")
  private Boolean isOnlyMaster;
  @ApiModelProperty(value="修改者")
  private Long updatedBy;
  @ApiModelProperty(value="删除者")
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

  public Permission toConvertEntity() {
    return Permission.builder()
                     .id(id)
                     .code(code)
                     .resource(resource)
                     .action(action)
                     .name(name)
                     .descritpion(descritpion)
                     .isOnlyMaster(isOnlyMaster)
                     .createdAt(createdAt)
                     .createdBy(createdBy)
                     .updatedAt(updatedAt)
                     .updatedBy(updatedBy)
                     .removedAt(removedAt)
                     .removedBy(removedBy)
                     .version(version)
                     .build();

  }
}