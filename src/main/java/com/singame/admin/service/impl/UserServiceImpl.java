package com.singame.admin.service.impl;

import java.util.List;

import com.singame.admin.domain.User;
import com.singame.admin.mapper.UserMapper;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.UserFilter;
import com.singame.admin.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("userService")
public class UserServiceImpl implements UserService {
  private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  @Autowired
  private UserMapper userMapper;

  @Override
  public Long create(User user) {
    return null;
  }

  @Override
  public void update(User funuserd) {
    
  }

  @Override
  public void delete(Long id) {
    
  }

  @Override
  public User getById(Long id) {
    return null;
  }

  @Override
  public User getByName(String name) {
    return null;
  }

  @Override
  public List<User> list(Query<UserFilter> query) {
    return null;
  }

  @Override
  public void freeze(Long id) {
    
  }

  @Override
  public void unfreeze(Long id) {
    
  }

  @Override
  public void disable(Long id) {
    
  }

  @Override
  public void dispatchRoles(Long id, List<Long> roleId) {
    
  }
}