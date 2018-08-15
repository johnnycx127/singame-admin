package com.singame.admin.service.impl;

import com.singame.admin.domain.Permission;
import com.singame.admin.service.PermissionService;
import com.singame.admin.mapper.UserMapper;
import com.singame.admin.mapper.RoleMapper;
import com.singame.admin.mapper.PermissionMapper;
import com.singame.admin.dto.PermissionTokenDTO;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
  @Autowired
  UserMapper userMapper;
  @Autowired
  RoleMapper roleMapper;
  @Autowired
  PermissionMapper permissionMapper;

  @Override
  public PermissionTokenDTO getpermissionlist(Long userId) {
    // 根据用户id来取出对应的权限列表
    PermissionTokenDTO permissionToken = new PermissionTokenDTO();

    List<Permission> permissions = permissionMapper.findPermissionByUserId(userId);
    List<String> urls = new ArrayList <>();
    for (Permission permission : permissions) {
      urls.add(permission.getUrl());
    }

    permissionToken.setAuthurls(urls);
    permissionToken.setUser(userMapper.getById(userId));
    permissionToken.setRoles(roleMapper.getById(userId));
    
    return permissionToken;
  }
}