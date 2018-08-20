package com.singame.admin.service.impl;

import com.singame.admin.domain.Permission;
import com.singame.admin.domain.User;
import com.singame.admin.exception.DataConflictException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.service.PermissionService;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;
import com.singame.admin.mapper.PermissionMapper;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

  private static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
  @Autowired
  PermissionMapper permissionMapper;

  @Override
  @Transactional
  public Long create(Permission permission, User operator) {
    permission.setCreatedBy(operator.getId());
    permission.setCreatedAt(LocalDateTime.now());
    permission.setUpdatedBy(operator.getId());
    permission.setUpdatedAt(LocalDateTime.now());
    permission.setRemovedBy(null);
    permission.setRemovedAt(null);
    return permissionMapper.add(permission);
  }
  @Override
  @Transactional
  public void update(Permission permission, User operator) throws NotFoundException, DataConflictException {
    Permission updatingPermission = permissionMapper.getById(permission.getId());
    if (updatingPermission == null) {
      throw new NotFoundException();
    }
    updatingPermission.setCode(permission.getCode());
    updatingPermission.setName(permission.getName());
    updatingPermission.setDescritpion(permission.getDescritpion());
    updatingPermission.setAction(permission.getAction());
    updatingPermission.setIsOnlyMaster(permission.getIsOnlyMaster());
    updatingPermission.setResource(permission.getResource());
    updatingPermission.setUpdatedBy(operator.getId());
    updatingPermission.setUpdatedAt(LocalDateTime.now());
    updatingPermission.setVersion(permission.getVersion());
    if (permissionMapper.update(updatingPermission) == 0) {
      throw new DataConflictException();
    }
  }
  @Override
  @Transactional
  public void delete(Long id, Integer version, User operator) throws NotFoundException, DataConflictException {
    if (permissionMapper.getById(id) == null) {
      throw new NotFoundException();
    }
    if (permissionMapper.delete(id, operator.getId(), LocalDateTime.now(), version) == 0) {
      throw new DataConflictException();
    }
  }
  @Override
  public Permission getById(Long id) throws NotFoundException {
    Permission p = permissionMapper.getById(id);
    if (p == null) {
      throw new NotFoundException();
    }
    return p;
  }
  @Override
  public Permission getByCode(String code) throws NotFoundException {
    Permission p = permissionMapper.getByCode(code);
    if (p == null) {
      throw new NotFoundException();
    }
    return p;
  }
  @Override
  public List<Permission> list(Query<PermissionFilter> query) {
    return permissionMapper.list(query);
  }
}