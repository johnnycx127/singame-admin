package com.singame.admin.mapper;

import com.singame.admin.domain.Role;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
  List<Role> getById(long userId); 
}
