package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.Role;
import com.singame.admin.domain.User;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.RoleFilter;

public interface RoleService {
  Long create(Role role, User operator);
  void update(Role role, User operator);
  void delete(Long id, Integer version, User operator);
  Role getById(Long id);
  List<Role> list(Query<RoleFilter> query);
  void dispatchPermission(Long id, List<Long> permissionIdList, Integer dispatchVersion, User operator);
}