package com.singame.admin.domain;

import org.joda.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@ApiModel(description = "用户")
public class User {
  private Long id;
  @NonNull private String name;
  @NonNull private String password;
  private Long createdBy;
  private Long updatedBy;
  private Long removedBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime removedAt;
  private Integer version;
}