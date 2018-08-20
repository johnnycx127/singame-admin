package com.singame.admin.query.filter;

import com.singame.admin.vo.Gender;
import com.singame.admin.vo.UserStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class UserFilter {
  private String code;
  private String codePartten;
  private String name;
  private String namePartten;
  private Gender gender;
  private UserStatus status;
  private Long roleId;
  private Long departmentId;
}