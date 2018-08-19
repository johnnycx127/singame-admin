package com.singame.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.LocalDateTime;

@Mapper
public interface RolePermissionMapper {
  Integer add(
    @Param("roleId") Long roleId,
    @Param("permissionId") Long permissionId,
    @Param("createdBy") Long createdBy,
    @Param("createdAt") LocalDateTime createdAt);
  Integer delete(
    @Param("roleId") Long roleId,
    @Param("permissionId") Long permissionId);
  Integer bulkDelete(
    @Param("roleId") Long roleId,
    @Param("permissionId") List<Long> permissionId);
  Integer clear(@Param("roleId") Long roleId);
}