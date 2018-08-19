package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.Permission;
import com.singame.admin.domain.User;
import com.singame.admin.exception.DataConflictException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;

public interface PermissionService {
  Long create(Permission permission, User operator);
  void update(Permission permission, User operator) throws NotFoundException, DataConflictException;
  void delete(Long id, Integer version, User operator) throws NotFoundException, DataConflictException;
  Permission getById(Long id)  throws NotFoundException;
  Permission getByCode(String code) throws NotFoundException;
  List<Permission> list(Query<PermissionFilter> query);
}