package com.singame.admin.mapper;

import com.singame.admin.domain.Permission;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
  Long add(Permission permission);
  void update(Permission permission);
  void delete(Long id);
  Permission getById(Long id);
  List<Permission> list(Query<PermissionFilter> query);
}
