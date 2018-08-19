package com.singame.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.LocalDateTime;

@Mapper
public interface UserRoleMapper {
  Integer add(
    @Param("userId") Long userId,
    @Param("roleId") Long roleId,
    @Param("createdBy") Long createdBy,
    @Param("createdAt") LocalDateTime createdAt);
  Integer delete(
    @Param("userId") Long userId,
    @Param("roleId") Long roleId);
  Integer bulkDelete(
    @Param("userId") Long userId,
    @Param("roleIdList") List<Long> roleIdList);
  Integer clear(@Param("userId") Long userId);
}