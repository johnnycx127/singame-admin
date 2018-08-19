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
    @Param("createdAt") LocalDateTime createdAt,
    @Param("version") Integer version);
  Integer delete(
    @Param("userId") Long userId,
    @Param("roleId") Long roleId,
    @Param("version") Integer version);
  Integer bulkDelete(
    @Param("userId") Long userId,
    @Param("roleIdList") List<Long> roleIdList,
    @Param("version") Integer version);
  Integer clear(
    @Param("userId") Long userId,
    @Param("version") Integer version);
}