package com.singame.admin.domain;

import com.singame.admin.dto.UserDTO;
import com.singame.admin.vo.Gender;
import com.singame.admin.vo.UserStatus;

import org.joda.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of="id")
public class User {
  private Long id;
  @NonNull String code;
  @NonNull private String password;
  @NonNull private String name;
  @NonNull private Gender gender;
  @NonNull private UserStatus status;
  private Long departmentId;
  private Boolean isDepartmentMaster;
  private String position;
  private Long createdBy;
  private Long updatedBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Integer version;
  private Integer dispatchRoleVersion;

  public UserDTO toConvertDTO() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(id);
    userDTO.setCode(code);
    userDTO.setName(name);
    userDTO.setGender(gender);
    userDTO.setStatus(status);
    userDTO.setDepartmentId(departmentId);
    userDTO.setPosition(position);
    userDTO.setCreatedBy(createdBy);
    userDTO.setCreatedAt(createdAt);
    userDTO.setUpdatedBy(updatedBy);
    userDTO.setUpdatedAt(updatedAt);
    userDTO.setVersion(version);
    userDTO.setDispatchRoleVersion(dispatchRoleVersion);
    return userDTO;
  }

  public void freeze() throws IllegalArgumentException {
    if (status != UserStatus.AVAILABLE) {
      throw new IllegalArgumentException("can only freeze [available] status user");
    }
    status = UserStatus.FREEZE;
  }

  public void unfreeze() throws IllegalArgumentException {
    if (status != UserStatus.FREEZE) {
      throw new IllegalArgumentException("can only unfreeze [freeze] status user");
    }
    status = UserStatus.AVAILABLE;
  }

  public void disable() throws IllegalArgumentException {
    if (status != UserStatus.AVAILABLE && status != UserStatus.FREEZE) {
      throw new IllegalArgumentException("can disable [available, freeze] status user");
    }
    status = UserStatus.UNAVAILABLE;
  }
}