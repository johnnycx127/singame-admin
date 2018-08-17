package com.singame.admin.mapper;

import java.util.List;

import com.singame.admin.domain.Department;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.DepartmentFilter;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
  Long add(Department permission);
  void update(Department permission);
  void delete(Long id);
  Department getById(Long id);
  List<Department> list(Query<DepartmentFilter> query);
}