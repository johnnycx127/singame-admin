package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.Role;
import com.singame.admin.domain.User;
import com.singame.admin.exception.DataConflictException;
import com.singame.admin.exception.DuplicateRecordException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.RoleFilter;

public interface RoleService {
  Long create(Role role, User operator) throws DuplicateRecordException;
  void update(Role role, User operator) throws DuplicateRecordException, NotFoundException, DataConflictException;
  void delete(Long id, Integer version, User operator) throws NotFoundException, DataConflictException;
  Role getById(Long id) throws NotFoundException;
  List<Role> list(Query<RoleFilter> query);
  void dispatchPermission(Long id, List<Long> permissionIdList, Integer dispatchVersion, User operator)
      throws DataConflictException;
}