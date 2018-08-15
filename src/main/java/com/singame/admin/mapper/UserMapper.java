package com.singame.admin.mapper;

import java.util.List;

import com.singame.admin.domain.User;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.UserFilter;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  Long add(User user);
  void update(User user);
  void delete(long userId);
  User getById(long userId);
  User getByName(String name);
  List<User> list(Query<UserFilter> query); 
}