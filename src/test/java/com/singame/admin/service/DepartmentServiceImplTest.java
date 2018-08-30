package com.singame.admin.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.singame.admin.domain.Department;
import com.singame.admin.domain.User;
import com.singame.admin.exception.DuplicateRecordException;
import com.singame.admin.mapper.DepartmentMapper;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.DepartmentFilter;
import com.singame.admin.service.impl.DepartmentServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DepartmentServiceImplTest {

  @TestConfiguration
  static class DepartmentServiceImplTestContextConfiguration {
    @Bean
    public DepartmentService departmentService() {
        return new DepartmentServiceImpl();
    }
  }

  @Autowired
  private DepartmentService departmentService;

  @MockBean
  private DepartmentMapper departmentMapper;

  @Test
  public void testAddDuplicatedDepartment() {
    Department dept = new Department();
    dept.setId(1L);
    dept.setName("dept0001");
    dept.setPid(null);
    User operator = new User();
    operator.setId(1L);
    Mockito.when(departmentMapper.add(dept)).thenReturn(dept.getId());

    // test duplicated name
    Query<DepartmentFilter> query = new Query<>();
    DepartmentFilter filter = new DepartmentFilter();
    filter.setName(dept.getName());
    query.setFilter(filter);

    List<Department> duplicatedDepts = new ArrayList<>();
    Department dupDept = new Department();
    BeanUtils.copyProperties(dept, dupDept);
    dupDept.setId(2L);
    duplicatedDepts.add(dupDept);
    Mockito.when(departmentMapper.list(query)).thenReturn(duplicatedDepts);
    try {
      departmentService.create(dept, operator);
      fail("expected action to throw, but it did not!");
    } catch(DuplicateRecordException e) {
      // pass
    }
    
  }

  @Test
  public void testAddDepartment() {
    Department dept = new Department();
    dept.setName("dept0001");
    dept.setPid(null);
    User operator = new User();
    operator.setId(1L);
    

    Mockito.when(departmentMapper.add(dept)).thenReturn(dept.getId());

    // test duplicated name
    Query<DepartmentFilter> query = new Query<>();
    DepartmentFilter filter = new DepartmentFilter();
    filter.setName(dept.getName() + "_other");
    query.setFilter(filter);
    List<Department> duplicatedDepts = new ArrayList<>();
    Mockito.when(departmentMapper.list(query)).thenReturn(duplicatedDepts);
    
    try {
      Long id = departmentService.create(dept, operator);
      assertEquals(dept.getId(), id);
    } catch (DuplicateRecordException e) {
      fail("expected action not to throw, but it did!");
    }
  }
}