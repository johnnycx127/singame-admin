package com.singame.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value="TokenResp", description="token返回对象")
public class TokenRespDTO {
  @ApiModelProperty(value="验权token")
  private String accessToken;
  @ApiModelProperty(value="刷新token")
  private String refreshToken;
}