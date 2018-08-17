package com.singame.admin.service.impl;

import java.util.List;

import com.singame.admin.domain.Department;
import com.singame.admin.mapper.DepartmentMapper;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.DepartmentFilter;
import com.singame.admin.service.DepartmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

  private static Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
  @Autowired
  private DepartmentMapper departmentMapper;

  @Override
  public Long create(Department role) {
    return null;
  }
  @Override
  public void update(Department role) {
    
  }
  @Override
  public void delete(Long id) {
    
  }
  @Override
  public Department getById(Long id) {
    return null;
  }
  @Override
  public List<Department> list(Query<DepartmentFilter> query) {
    return null;
  }
}