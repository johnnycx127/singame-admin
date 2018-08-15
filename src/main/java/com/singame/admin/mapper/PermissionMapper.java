package com.singame.admin.mapper;

import com.singame.admin.domain.Permission;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    public List<Permission> findPermissionByUserId(Long userId);
}
