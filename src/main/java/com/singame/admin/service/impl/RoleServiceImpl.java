package com.singame.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.singame.admin.domain.Permission;
import com.singame.admin.domain.Role;
import com.singame.admin.domain.User;
import com.singame.admin.exception.DataConflictException;
import com.singame.admin.exception.DuplicateRecordException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.mapper.PermissionMapper;
import com.singame.admin.mapper.RoleMapper;
import com.singame.admin.mapper.RolePermissionMapper;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;
import com.singame.admin.query.filter.RoleFilter;
import com.singame.admin.service.RoleService;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

  private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private PermissionMapper permissionMapper;
  @Autowired
  private RolePermissionMapper rolePermissionMapper;

  private void isDuplicatedName(Long id, String name) throws DuplicateRecordException {
    RoleFilter filter = new RoleFilter();
    filter.setName(name);
    Query<RoleFilter> query = new Query<>();
    List<Role> roles = roleMapper.list(query);
    if (roles.size() > 1) {
      throw new DuplicateRecordException("名称重复");
    }
    if (roles.size() > 0 && roles.get(1).getId() != id) {
      throw new DuplicateRecordException("名称重复");
    }
  }

  @Override
  @Transactional
	public Long create(Role role, User operator) throws DuplicateRecordException {
    isDuplicatedName(role.getId(), role.getName());
    role.setCreatedBy(operator.getId());
    role.setCreatedAt(LocalDateTime.now());
    role.setUpdatedBy(operator.getId());
    role.setUpdatedAt(LocalDateTime.now());
    role.setRemovedBy(null);
    role.setRemovedAt(null);
		return roleMapper.add(role);
	}

  @Override
  @Transactional
	public void update(Role role, User operator) throws DuplicateRecordException, NotFoundException, DataConflictException {
    Role updatingRole = roleMapper.getById(role.getId());
    if (updatingRole == null) {
      throw new NotFoundException();
    }
    updatingRole.setName(role.getName());
    updatingRole.setVersion(role.getVersion());
    updatingRole.setUpdatedBy(operator.getId());
    updatingRole.setUpdatedAt(LocalDateTime.now());
    isDuplicatedName(updatingRole.getId(), updatingRole.getName());
    if (roleMapper.update(updatingRole) == 0) {
      throw new DataConflictException();
    }
	}

  @Override
  @Transactional
	public void delete(Long id, Integer version, User operator) throws NotFoundException, DataConflictException {
    if (roleMapper.getById(id) == null) {
      throw new NotFoundException();
    }
    if (roleMapper.delete(id, operator.getId(), LocalDateTime.now(), version) == 0) {
      throw new DataConflictException();
    }
	}

	@Override
	public Role getById(Long id) throws NotFoundException {
    Role role = roleMapper.getById(id);
    if (role == null) {
      throw new NotFoundException();
    }
		return role;
	}

	@Override
	public List<Role> list(Query<RoleFilter> query) {
		return roleMapper.list(query);
	}

  @Override
  @Transactional
  public void dispatchPermission(Long id, List<Long> permissionIdList, Integer dispatchVersion, User operator)
      throws DataConflictException{
    PermissionFilter pFilter = new PermissionFilter();
    pFilter.setRoleId(id);
    Query<PermissionFilter> pQuery = new Query<>();
    pQuery.setFilter(pFilter);
    List<Permission> permissions = permissionMapper.list(pQuery);
    List<Long> dispatchedPIdList = permissions.stream().map(p -> p.getId()).collect(Collectors.toList());
    dispatchedPIdList.stream()
                     .filter(dispatchedId -> !permissionIdList.contains(dispatchedId))
                     .forEach(deletingId -> rolePermissionMapper.delete(id, deletingId));
    permissionIdList.stream()
                    .filter(pId -> !dispatchedPIdList.contains(pId))
                    .forEach(addingId -> rolePermissionMapper.add(id, addingId, operator.getId(), LocalDateTime.now()));
    if (roleMapper.incDispatchPermissionVersion(dispatchVersion) == 0) {
      throw new DataConflictException();
    }
	}

}