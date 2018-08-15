package com.singame.admin.mapper;

import com.singame.admin.domain.Role;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.RoleFilter;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
  Long add(Role role);
  void update(Role role);
  void delete(Long id);
  Role getById(Long id);
  List<Role> list(Query<RoleFilter> query);
}
