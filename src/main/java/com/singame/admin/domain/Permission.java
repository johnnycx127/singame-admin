package com.singame.admin.domain;

import org.joda.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Permission {
  private Long id;
  private String resource;
  private String name;
  private String descritpion;
  private Long createdBy;
  private Long updatedBy;
  private Long removedBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime removedAt;
  private Integer version;
}
