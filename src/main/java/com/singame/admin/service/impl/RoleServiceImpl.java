package com.singame.admin.service.impl;

import java.util.List;

import com.singame.admin.domain.Role;
import com.singame.admin.mapper.RoleMapper;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.RoleFilter;
import com.singame.admin.service.RoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImpl implements RoleService {

  private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
  @Autowired
  private RoleMapper roleMapper;

	@Override
	public Long create(Role role) {
		return null;
	}

	@Override
	public void update(Role role) {
		
	}

	@Override
	public void delete(Long id) {
		
	}

	@Override
	public Role getById(Long id) {
		return null;
	}

	@Override
	public List<Role> list(Query<RoleFilter> query) {
		return null;
	}

	@Override
	public void dispatchPermission(Long id, List<Long> permissionIdList) {
		
	}

}