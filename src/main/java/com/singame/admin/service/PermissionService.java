package com.singame.admin.service;

import com.singame.admin.dto.PermissionTokenDTO;

public interface PermissionService {
  PermissionTokenDTO getpermissionlist(Long userid);
}