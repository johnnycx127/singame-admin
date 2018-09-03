package com.singame.admin.domain;

import com.singame.admin.dto.UserDTO;
import com.singame.admin.vo.Gender;
import com.singame.admin.vo.UserStatus;

import org.joda.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@Builder
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
    return UserDTO.builder()
                  .id(id)
                  .code(code)
                  .name(name)
                  .gender(gender)
                  .status(status)
                  .departmentId(departmentId)
                  .position(position)
                  .createdBy(createdBy)
                  .createdAt(createdAt)
                  .updatedBy(updatedBy)
                  .updatedAt(updatedAt)
                  .version(version)
                  .dispatchRoleVersion(dispatchRoleVersion)
                  .build();
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