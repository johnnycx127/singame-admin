package com.singame.admin.domain;

import com.singame.admin.dto.PermissionDTO;
import com.singame.admin.vo.PermissionAction;

import org.joda.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@Builder
@EqualsAndHashCode(of="id")
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
    return PermissionDTO.builder()
                        .id(id)
                        .code(code)
                        .resource(resource)
                        .action(action)
                        .name(name)
                        .descritpion(descritpion)
                        .isOnlyMaster(isOnlyMaster)
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
