package com.singame.admin.domain;

import com.singame.admin.vo.Gender;
import com.singame.admin.vo.UserStatus;

import org.joda.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
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
}