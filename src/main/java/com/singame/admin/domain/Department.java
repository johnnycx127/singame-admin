package com.singame.admin.domain;

import com.singame.admin.dto.DepartmentDTO;

import org.joda.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@Builder
@EqualsAndHashCode(of="id")
public class Department {
  private Long id;
  private Long pid;
  private String name;
  private String description;
  private Long createdBy;
  private Long updatedBy;
  private Long removedBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime removedAt;
  private Integer version;

  public DepartmentDTO toConvertDTO() {
    return DepartmentDTO.builder()
                        .id(id)
                        .pid(pid)
                        .name(name)
                        .description(description)
                        .createdAt(createdAt)
                        .createdBy(createdBy)
                        .updatedAt(updatedAt)
                        .updatedBy(updatedBy)
                        .removedAt(removedAt)
                        .removedBy(removedBy)
                        .version(version)
                        .build();
  }
}