package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.User;
import com.singame.admin.dto.UserDTO;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.UserFilter;

public interface UserService {
  Long create(UserDTO fund);
  void update(UserDTO fund);
  void delete(Long id);
  User getById(Long id);
  User getByName(String name);
  List<User> list(Query<UserFilter> query);
}