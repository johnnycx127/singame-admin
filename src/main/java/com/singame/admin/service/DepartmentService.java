package com.singame.admin.service;

import java.util.List;

import com.singame.admin.domain.Department;
import com.singame.admin.domain.User;
import com.singame.admin.exception.DataConflictException;
import com.singame.admin.exception.DuplicateRecordException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.DepartmentFilter;

public interface DepartmentService {
  Long create(Department department, User operator) throws DuplicateRecordException;
  void update(Department department, User operator) throws DuplicateRecordException, NotFoundException, DataConflictException;
  void delete(Long id, Integer version, User operator) throws NotFoundException, DataConflictException;
  Department getById(Long id) throws NotFoundException;
  List<Department> list(Query<DepartmentFilter> query) throws NotFoundException;
}