package com.singame.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.singame.admin.domain.Role;
import com.singame.admin.domain.User;
import com.singame.admin.exception.DataConflictException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.mapper.RoleMapper;
import com.singame.admin.mapper.UserMapper;
import com.singame.admin.mapper.UserRoleMapper;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.RoleFilter;
import com.singame.admin.query.filter.UserFilter;
import com.singame.admin.service.UserService;
import com.singame.admin.vo.UserStatus;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {
  private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private UserRoleMapper userRoleMapper;

  @Override
  @Transactional
  public Long create(User user, User operator) {
    user.setStatus(UserStatus.AVAILABLE);
    user.setCreatedBy(operator.getId());
    user.setUpdatedBy(operator.getId());
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());
    Long id = userMapper.add(user);
    return id;
  }

  @Override
  @Transactional
  public void update(User user, User operator) throws NotFoundException, DataConflictException {
    User updatingUser = userMapper.getById(user.getId());
    if (updatingUser == null) {
      throw new NotFoundException();
    }
    updatingUser.setCode(user.getCode());
    updatingUser.setName(user.getName());
    updatingUser.setGender(user.getGender());
    updatingUser.setDepartmentId(user.getDepartmentId());
    updatingUser.setIsDepartmentMaster(user.getIsDepartmentMaster());
    updatingUser.setPosition(user.getPosition());
    updatingUser.setUpdatedBy(operator.getId());
    updatingUser.setUpdatedAt(LocalDateTime.now());
    updatingUser.setVersion(user.getVersion());
    Integer rowNum = userMapper.update(updatingUser);
    if (rowNum == 0) {
      throw new DataConflictException();
    }
  }

  @Override
  public User getById(Long id) throws NotFoundException {
    User user = userMapper.getById(id);
    if (user == null) {
      throw new NotFoundException();
    }
    return user;
  }

  @Override
  public User getByCode(String code) throws NotFoundException {
    User user = userMapper.getByCode(code);
    if (user == null) {
      throw new NotFoundException();
    }
    return user;
  }

  @Override
  public List<User> list(Query<UserFilter> query) {
    return userMapper.list(query);
  }

  @Override
  public void freeze(Long id, User operator) throws NotFoundException, DataConflictException {
    User user = userMapper.getById(id);
    if (user == null) {
      throw new NotFoundException();
    }
    user.freeze();
    Integer rowNum = userMapper.update(user);
    if (rowNum == 0) {
      throw new DataConflictException();
    }
  }

  @Override
  public void unfreeze(Long id, User operator) throws NotFoundException, DataConflictException {
    User user = userMapper.getById(id);
    if (user == null) {
      throw new NotFoundException();
    }
    user.unfreeze();
    Integer rowNum = userMapper.update(user);
    if (rowNum == 0) {
      throw new DataConflictException();
    }
  }

  @Override
  public void disable(Long id, User operator) throws NotFoundException, DataConflictException {
    User user = userMapper.getById(id);
    if (user == null) {
      throw new NotFoundException();
    }
    user.disable();
    Integer rowNum = userMapper.update(user);
    if (rowNum == 0) {
      throw new DataConflictException();
    }
  }

  @Override
  @Transactional
  public void dispatchRoles(Long id, List<Long> roleIdList, Integer dispatchVersion, User operator) throws NotFoundException, DataConflictException {
    RoleFilter roleFilter = new RoleFilter();
    roleFilter.setUserId(id);
    Query<RoleFilter> query = new Query<>();
    query.setFilter(roleFilter);
    List<Role> roles = roleMapper.list(query);
    List<Long> storedRoleIdList = roles.stream().map(role -> role.getId()).collect(Collectors.toList());
    storedRoleIdList.stream()
                    .filter(storedId -> !roleIdList.contains(storedId))
                    .forEach(deletingId -> userRoleMapper.delete(id, deletingId, dispatchVersion));
    roleIdList.stream()
              .filter(roleId -> !storedRoleIdList.contains(roleId))
              .forEach(addingId -> userRoleMapper.add(id, addingId, operator.getId(), LocalDateTime.now(), dispatchVersion));
    Integer rowNum = userMapper.incDispatchRoleVersion(dispatchVersion);
    if (rowNum == 0) {
      throw new DataConflictException();
    }
  }
}