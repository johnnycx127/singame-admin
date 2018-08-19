package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.User;
import com.singame.admin.exception.DataConflictException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.UserFilter;

public interface UserService {
  Long create(User user, User operator);
  void update(User user, User operator) throws NotFoundException, DataConflictException;
  User getById(Long id) throws NotFoundException;
  User getByCode(String Code) throws NotFoundException;
  List<User> list(Query<UserFilter> query);
  void freeze(Long id, User Operator) throws NotFoundException, DataConflictException;
  void unfreeze(Long id, User Operator) throws NotFoundException, DataConflictException;
  void disable(Long id, User Operator) throws NotFoundException, DataConflictException;
  void dispatchRoles(Long id, List<Long> roleIdList, Integer dispatchVersion, User Operator)
      throws NotFoundException, DataConflictException;
}