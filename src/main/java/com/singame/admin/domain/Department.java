package com.singame.admin.domain;

import com.singame.admin.dto.DepartmentDTO;

import org.joda.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
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
    DepartmentDTO departmentDTO = new DepartmentDTO();
    departmentDTO.setId(id);
    departmentDTO.setPid(pid);
    departmentDTO.setName(name);
    departmentDTO.setDescription(description);
    departmentDTO.setCreatedBy(createdBy);
    departmentDTO.setCreatedAt(createdAt);
    departmentDTO.setUpdatedBy(updatedBy);
    departmentDTO.setUpdatedAt(updatedAt);
    departmentDTO.setRemovedBy(removedBy);
    departmentDTO.setRemovedAt(removedAt);
    return departmentDTO;
  }
}