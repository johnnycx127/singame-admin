package com.singame.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.singame.admin.domain.Department;

import org.joda.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Builder
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value="DepartmentDTO", description="部门")
public class DepartmentDTO {
  private Long id;
  @ApiModelProperty(value="父部门ID")
  private Long pid;
  @ApiModelProperty(value="名称")
  private String name;
  @ApiModelProperty(value="描述")
  private String description;
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

  public Department toConvertEntity() {
    return Department.builder()
                     .id(id)
                     .pid(pid)
                     .name(name)
                     .description(description)
                     .createdAt(createdAt)
                     .createdBy(createdBy)
                     .updatedAt(updatedAt)
                     .updatedBy(updatedBy)
                     .version(version)
                     .build();
  }
}