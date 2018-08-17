package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.User;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.UserFilter;

public interface UserService {
  Long create(User user);
  void update(User funuserd);
  void delete(Long id);
  User getById(Long id);
  User getByName(String name);
  List<User> list(Query<UserFilter> query);
  void freeze(Long id);
  void unfreeze(Long id);
  void disable(Long id);
  void dispatchRoles(Long id, List<Long> roleId);
}