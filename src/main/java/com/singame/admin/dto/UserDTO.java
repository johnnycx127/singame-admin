package com.singame.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.joda.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@ApiModel(value = "User", description = "用户")
public class UserDTO {
  private Long id;
  @ApiModelProperty(value = "姓名", required = true)
  @NonNull private String name;
  @ApiModelProperty(value = "密码", required = true)
  @NonNull private String password;
  private String keyword;
  @ApiModelProperty(value = "创建人")
  private Long createdBy;
  @ApiModelProperty(value = "修改人")
  private Long updatedBy;
  @ApiModelProperty(value = "删除人")
  private Long removedBy;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @ApiModelProperty(example = "2017-01-01 00:00:00")
  private LocalDateTime createdAt;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @ApiModelProperty(example = "2017-01-01 00:00:00")
  private LocalDateTime updatedAt;
  @ApiModelProperty(example = "2017-01-01 00:00:00")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime removedAt;
}