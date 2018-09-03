package com.singame.admin.service.impl;

import java.util.List;

import com.singame.admin.domain.Department;
import com.singame.admin.domain.User;
import com.singame.admin.exception.DataConflictException;
import com.singame.admin.exception.DuplicateRecordException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.mapper.DepartmentMapper;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.DepartmentFilter;
import com.singame.admin.service.DepartmentService;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

  @Autowired
  private DepartmentMapper departmentMapper;

  private void isDuplicatedName(Long id, String name) throws DuplicateRecordException {
    Query<DepartmentFilter> query = Query.<DepartmentFilter>builder()
                                         .filter(DepartmentFilter.builder().name(name).build())
                                         .build();
    List<Department> depts = departmentMapper.list(query);
    if (depts.size() > 1) {
      throw new DuplicateRecordException();
    }
    if (depts.size() > 0 && depts.get(0).getId() != id) {
      throw new DuplicateRecordException();
    }
  }

  @Override
  @Transactional
  public Long create(Department department, User operator) throws DuplicateRecordException {
    isDuplicatedName(department.getId(), department.getName());
    department.setCreatedBy(operator.getId());
    department.setCreatedAt(LocalDateTime.now());
    department.setUpdatedBy(operator.getId());
    department.setUpdatedAt(LocalDateTime.now());
    department.setRemovedBy(null);
    department.setRemovedAt(null);
    return departmentMapper.add(department);
  }

  @Override
  @Transactional
  public void update(Department department, User operator) throws DuplicateRecordException, NotFoundException, DataConflictException {
    Department updatingDept = departmentMapper.getById(department.getId());
    if (updatingDept == null) {
      throw new NotFoundException();
    }
    updatingDept.setUpdatedBy(operator.getId());
    updatingDept.setUpdatedAt(LocalDateTime.now());
    updatingDept.setVersion(department.getVersion());
    isDuplicatedName(updatingDept.getId(), updatingDept.getName());
    if (departmentMapper.update(updatingDept) == 0) {
      throw new DataConflictException();
    }
  }
  @Override
  @Transactional
  public void delete(Long id, Integer version, User operator) throws NotFoundException, DataConflictException {
    if (departmentMapper.getById(id) == null) {
      throw new NotFoundException();
    }
    if (departmentMapper.delete(id, operator.getId(), LocalDateTime.now(), version) == 0) {
      throw new DataConflictException();
    }
  }
  @Override
  public Department getById(Long id) throws NotFoundException {
    Department dept = departmentMapper.getById(id);
    if (dept == null) {
      throw new NotFoundException();
    }
    return dept;
  }
  @Override
  public List<Department> list(Query<DepartmentFilter> query) {
    return departmentMapper.list(query);
  }
}