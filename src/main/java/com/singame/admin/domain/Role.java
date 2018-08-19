package com.singame.admin.domain;

import com.singame.admin.dto.RoleDTO;

import org.joda.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Role {
  private Long id;
  private String name;
  private Long createdBy;
  private Long updatedBy;
  private Long removedBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime removedAt;
  private Integer version;
  private Integer dispatchPermissionVersion;

  public RoleDTO toConvertDTO() {
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setId(id);
    roleDTO.setName(name);
    roleDTO.setCreatedBy(createdBy);
    roleDTO.setCreatedAt(createdAt);
    roleDTO.setUpdatedBy(updatedBy);
    roleDTO.setUpdatedAt(updatedAt);
    roleDTO.setRemovedBy(removedBy);
    roleDTO.setRemovedAt(removedAt);
    roleDTO.setVersion(version);
    roleDTO.setDispatchPermissionVersion(dispatchPermissionVersion);
    return roleDTO;
  }
}
