package com.singame.admin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.singame.admin.domain.User;
import com.singame.admin.vo.Gender;
import com.singame.admin.vo.UserStatus;

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
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value = "UserDTO", description = "用户返回结果")
public class UserDTO {
  private Long id;
  @ApiModelProperty(value = "编码", required = true)
  @NonNull
  private String code;
  @ApiModelProperty(value = "密码", required = true)
  @NonNull
  private String password;
  @ApiModelProperty(value = "姓名", required = true)
  @NonNull
  private String name;
  @ApiModelProperty(value = "性别 UNKNOW-未知 MALE-男 FEMALE-女")
  @NonNull
  private Gender gender;
  @ApiModelProperty(value = "用户状态 UNKNOW-未知 AVAILABLE-可用 FREEZE-冻结 UNAVAILABLE-不可用")
  @NonNull
  private UserStatus status;
  @ApiModelProperty(value = "部门ID")
  private Long departmentId;
  @ApiModelProperty(value = "是否为部门主管")
  private Boolean isDepartmentMaster;
  @ApiModelProperty(value = "职位")
  private String position;
  @ApiModelProperty(value = "创建人")
  private Long createdBy;
  @ApiModelProperty(value = "修改人")
  private Long updatedBy;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @ApiModelProperty(example = "2017-01-01 00:00:00")
  private LocalDateTime createdAt;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @ApiModelProperty(example = "2017-01-01 00:00:00")
  private LocalDateTime updatedAt;

  public User toConvertEntity() {
    User user = new User();
    user.setId(id);
    user.setCode(code);
    user.setName(name);
    user.setGender(gender);
    user.setStatus(status);
    user.setDepartmentId(departmentId);
    user.setPosition(position);
    user.setCreatedBy(createdBy);
    user.setCreatedAt(createdAt);
    user.setUpdatedBy(updatedBy);
    user.setUpdatedAt(updatedAt);
    return user;
  }
}