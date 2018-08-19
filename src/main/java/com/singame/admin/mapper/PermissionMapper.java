package com.singame.admin.mapper;

import com.singame.admin.domain.Permission;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.LocalDateTime;

import java.util.List;

@Mapper
public interface PermissionMapper {
  Long add(Permission permission);
  Integer update(Permission permission);
  Integer delete(
    @Param("id") Long id,
    @Param("removedBy") Long removedBy,
    @Param("removedAt") LocalDateTime removedAt,
    @Param("version") Integer version);
  Permission getById(Long id);
  Permission getByCode(String code);
  List<Permission> list(Query<PermissionFilter> query);
}
