package com.singame.admin.mapper;

import com.singame.admin.domain.Role;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.RoleFilter;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.LocalDateTime;

import java.util.List;

@Mapper
public interface RoleMapper {
  Long add(Role role);
  Integer update(Role role);
  Integer delete(
    @Param("id") Long id,
    @Param("removedBy") Long removedBy,
    @Param("removedAt") LocalDateTime removedAt,
    @Param("version") Integer version);
  Role getById(Long id);
  List<Role> list(Query<RoleFilter> query);
  Integer incDispatchPermissionVersion(Integer version);
}
