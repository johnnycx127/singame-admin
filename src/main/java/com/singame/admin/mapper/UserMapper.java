package com.singame.admin.mapper;

import java.util.List;

import com.singame.admin.domain.User;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.UserFilter;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  Long add(User user);
  Integer update(User user);
  User getById(Long userId);
  User getByCode(String code);
  List<User> list(Query<UserFilter> query);
  Integer incDispatchRoleVersion(Integer version);
}