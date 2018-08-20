package com.singame.admin.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.singame.admin.common.ReplyBizStatus;
import com.singame.admin.common.ReqAttrKey;
import com.singame.admin.common.Reply;
import com.singame.admin.domain.Department;
import com.singame.admin.domain.User;
import com.singame.admin.dto.DepartmentDTO;
import com.singame.admin.dto.UserAuthDTO;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.DepartmentFilter;
import com.singame.admin.service.DepartmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentCtrl {
  private static Logger logger = LoggerFactory.getLogger(DepartmentCtrl.class);

  @Autowired
  private DepartmentService departmentService;

  @ApiOperation(value="新建部门", notes="新建部门")
  @RequestMapping(value="", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<DepartmentDTO> createDepartment(
      @ApiParam @RequestBody DepartmentDTO departmentDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("departmentDTO is \n {} \n", departmentDTO.toString());
    Department department = departmentDTO.toConvertEntity();
    logger.trace("department is \n {} \n", department.toString());
    Long id = departmentService.create(department, operator);
    return new Reply<>(ReplyBizStatus.OK, "success", department.toConvertDTO());
  }

  @ApiOperation(value="更新部门", notes="更新部门")
  @RequestMapping(value="/{departmentId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> updateDepartment(
      @ApiParam @PathVariable("departmentId") Long departmentId,
      @ApiParam @RequestBody DepartmentDTO departmentDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("departmentId is {}, departmentDTO is \n {} \n", departmentId.toString(), departmentDTO.toString());
    Department department = departmentDTO.toConvertEntity();
    department.setId(departmentId);
    logger.trace("department is \n {} \n", department.toString());
    departmentService.update(department, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="删除部门", notes="删除部门")
  @RequestMapping(value="/{departmentId}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> deleteDepartment(
      @ApiParam @PathVariable("departmentId") Long departmentId,
      @ApiParam @PathVariable("version") Integer version,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("departmentId is {}\n", departmentId.toString());
    departmentService.delete(departmentId, version, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="按ID获取部门", notes="按ID获取部门")
  @RequestMapping(value="/{departmentId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<DepartmentDTO> getDepartment(
      @ApiParam @PathVariable("departmentId") Long departmentId) throws Exception {
    logger.trace("departmentId is {} \n", departmentId.toString());
    Department department = departmentService.getById(departmentId);
    logger.trace("department is \n {} \n", department.toString());
    DepartmentDTO departmentDTO = department.toConvertDTO();
    logger.trace("departmentDTO is \n {} \n", departmentDTO.toString());
    return new Reply<>(ReplyBizStatus.OK, "success", departmentDTO);
  }

  @ApiOperation(value="查询部门列表", notes="查询部门列表")
  @RequestMapping(value="/query", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<List<DepartmentDTO>> listDepartment(@ApiParam @RequestBody Query<DepartmentFilter> query) throws Exception {
    logger.trace("department query is {} \n", query.toString());
    List<Department> departments = departmentService.list(query);
    logger.trace("department list is [\n {} \n]", Joiner.on("\t\n").join(departments));
    return new Reply<>(ReplyBizStatus.OK, "success",
        departments.stream().map(department -> department.toConvertDTO()).collect(Collectors.toList()));
  }
}