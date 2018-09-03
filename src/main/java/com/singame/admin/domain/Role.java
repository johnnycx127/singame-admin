package com.singame.admin.domain;

import com.singame.admin.dto.RoleDTO;

import org.joda.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@Builder
@EqualsAndHashCode(of="id")
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
    return RoleDTO.builder()
                  .id(id)
                  .name(name)
                  .createdAt(createdAt)
                  .createdBy(createdBy)
                  .updatedAt(updatedAt)
                  .updatedBy(updatedBy)
                  .removedAt(removedAt)
                  .removedBy(removedBy)
                  .version(version)
                  .dispatchPermissionVersion(dispatchPermissionVersion)
                  .build();
  }
}
