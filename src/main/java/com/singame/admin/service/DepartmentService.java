package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.Department;
import com.singame.admin.domain.User;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.DepartmentFilter;

public interface DepartmentService {
  Long create(Department department, User operator);
  void update(Department department, User operator);
  void delete(Long id, Integer version, User operator);
  Department getById(Long id);
  List<Department> list(Query<DepartmentFilter> query);
}