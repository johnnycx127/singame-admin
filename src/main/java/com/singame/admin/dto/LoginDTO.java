package com.singame.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode
@ApiModel(value="LoginReq", description="登录请求")
public class LoginDTO {
  @ApiModelProperty(value="用户编码")
  private String code;
  @ApiModelProperty(value="用户密码")
  private String password;
}