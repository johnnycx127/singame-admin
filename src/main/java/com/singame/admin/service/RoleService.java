package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.Role;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.RoleFilter;

public interface RoleService {
  Long create(Role role);
  void update(Role role);
  void delete(Long id);
  Role getById(Long id);
  List<Role> list(Query<RoleFilter> query);
  void dispatchPermission(Long id, List<Long> permissionIdList);
}