package com.singame.admin.service.impl;

import java.util.List;

import com.singame.admin.domain.User;
import com.singame.admin.dto.UserDTO;
import com.singame.admin.mapper.UserMapper;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.UserFilter;
import com.singame.admin.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("userService")
public class UserServiceImpl implements UserService {
  @Autowired
  private UserMapper userMapper;
  @Override
  public Long create(UserDTO u) {
    User user = new User();
    user.setName(u.getName());
    user.setPassword(u.getPassword());
    System.out.println("the key is " + u.getKeyword());
    user.setCreatedAt(u.getCreatedAt());
    user.setCreatedBy(u.getCreatedBy());
    return userMapper.add(user);
  }
  @Override
  public void update(UserDTO u) {
    
  }
  @Override
  public void delete(Long id) {
    
  }
  @Override
  public User getById(Long id) {
    return null;
  }
  @Override
  public List<User> list(Query<UserFilter> query) {
    return null;
  }
  @Override
  public User getByName(String name) {
    return userMapper.getByName(name);
  }
}