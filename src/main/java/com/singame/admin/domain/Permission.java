package com.singame.admin.domain;

import com.singame.admin.dto.PermissionDTO;
import com.singame.admin.vo.PermissionAction;

import org.joda.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Permission {
  private Long id;
  private String code;
  private String resource;
  private PermissionAction action;
  private String name;
  private String descritpion;
  private Boolean isOnlyMaster;
  private Long createdBy;
  private Long updatedBy;
  private Long removedBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime removedAt;
  private Integer version;

  public PermissionDTO toConvertDTO() {
    PermissionDTO permissionDTO = new  PermissionDTO();
    permissionDTO.setId(id);
    permissionDTO.setCode(code);
    permissionDTO.setResource(resource);
    permissionDTO.setAction(action);
    permissionDTO.setName(name);
    permissionDTO.setDescritpion(descritpion);
    permissionDTO.setIsOnlyMaster(isOnlyMaster);
    permissionDTO.setCreatedBy(createdBy);
    permissionDTO.setCreatedAt(createdAt);
    permissionDTO.setUpdatedBy(updatedBy);
    permissionDTO.setUpdatedAt(updatedAt);
    permissionDTO.setRemovedBy(removedBy);
    permissionDTO.setRemovedAt(removedAt);
    permissionDTO.setVersion(version);
    return permissionDTO;
  }
}
