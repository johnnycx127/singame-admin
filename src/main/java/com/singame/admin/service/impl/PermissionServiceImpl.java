package com.singame.admin.service.impl;

import com.singame.admin.domain.Permission;
import com.singame.admin.service.PermissionService;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;
import com.singame.admin.mapper.PermissionMapper;

import java.util.List;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

  private static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
  @Autowired
  PermissionMapper permissionMapper;

  @Override
  public Long create(Permission permission) {
    return null;
  }
  @Override
  public void update(Permission permission) {
    
  }
  @Override
  public void delete(Long id) {
    
  }
  @Override
  public Permission getById(Long id) {
    return null;
  }
  @Override
  public Permission getByCode(String code) {
    return null;
  }
  @Override
  public List<Permission> list(Query<PermissionFilter> query) {
    return null;
  }
}