package com.singame.admin.service.impl;

import com.singame.admin.domain.Permission;
import com.singame.admin.domain.User;
import com.singame.admin.exception.DataConflictException;
import com.singame.admin.exception.DuplicateRecordException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.service.PermissionService;
import com.singame.admin.vo.PermissionAction;
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

  private void isDuplicatedCode(Long id, String code) throws DuplicateRecordException {
    Permission p = permissionMapper.getByCode(code);
    if (p != null && p.getId() != id) {
      throw new DuplicateRecordException();
    }
  }

  private void isDuplicatedName(Long id, String name) throws DuplicateRecordException {
    PermissionFilter filter = new PermissionFilter();
    filter.setName(name);
    Query<PermissionFilter> query = new Query<>();
    List<Permission> permissions = permissionMapper.list(query);
    if (permissions.size() > 1) {
      throw new DuplicateRecordException();
    }
    if (permissions.size() > 0 && permissions.get(1).getId() != id) {
      throw new DuplicateRecordException();
    }
  }

  private void isDuplicatedResourceAndAction(Long id, String resource, PermissionAction action)
      throws DuplicateRecordException {
    PermissionFilter filter = new PermissionFilter();
    filter.setResource(resource);
    filter.setAction(action);
    Query<PermissionFilter> query = new Query<>();
    List<Permission> permissions = permissionMapper.list(query);
    if (permissions.size() > 1) {
      throw new DuplicateRecordException();
    }
    if (permissions.size() > 0 && permissions.get(1).getId() != id) {
      throw new DuplicateRecordException();
    }
  }

  @Override
  @Transactional
  public Long create(Permission permission, User operator) throws DuplicateRecordException {
    isDuplicatedCode(permission.getId(), permission.getCode());
    isDuplicatedName(permission.getId(), permission.getName());
    isDuplicatedResourceAndAction(permission.getId(), permission.getResource(), permission.getAction());
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
  public void update(Permission permission, User operator) throws DuplicateRecordException, NotFoundException, DataConflictException {
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
    isDuplicatedCode(updatingPermission.getId(), updatingPermission.getCode());
    isDuplicatedName(updatingPermission.getId(), updatingPermission.getName());
    isDuplicatedResourceAndAction(updatingPermission.getId(), updatingPermission.getResource(), updatingPermission.getAction());
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