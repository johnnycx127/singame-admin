package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.Permission;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;

public interface PermissionService {
  Long create(Permission permission);
  void update(Permission permission);
  void delete(Long id);
  Permission getById(Long id);
  Permission getByCode(String code);
  List<Permission> list(Query<PermissionFilter> query);
}