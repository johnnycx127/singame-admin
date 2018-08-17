package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.Department;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.DepartmentFilter;

public interface DepartmentService {
  Long create(Department role);
  void update(Department role);
  void delete(Long id);
  Department getById(Long id);
  List<Department> list(Query<DepartmentFilter> query);
}