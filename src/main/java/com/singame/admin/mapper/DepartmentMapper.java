package com.singame.admin.mapper;

import java.util.List;

import com.singame.admin.domain.Department;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.DepartmentFilter;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.LocalDateTime;

@Mapper
public interface DepartmentMapper {
  Long add(Department permission);
  Integer update(Department permission);
  Integer delete(
    @Param("id") Long id,
    @Param("removedBy") Long removedBy,
    @Param("removedAt") LocalDateTime removedAt,
    @Param("version") Integer version);
  Department getById(Long id);
  List<Department> list(Query<DepartmentFilter> query);
}