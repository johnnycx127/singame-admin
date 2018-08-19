package com.singame.admin.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.singame.admin.common.ReplyBizStatus;
import com.singame.admin.common.ReqAttrKey;
import com.singame.admin.common.ResponseBody;
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

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentCtrl {
  private static Logger logger = LoggerFactory.getLogger(DepartmentCtrl.class);

  @Autowired
  private DepartmentService departmentService;

  @ApiOperation(value="新建部门", notes="新建部门")
  @ApiImplicitParams({
    @ApiImplicitParam(name="department", value="部门", required=true, dataType="Department", paramType="body")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<DepartmentDTO> createDepartment(
      @RequestBody DepartmentDTO departmentDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("departmentDTO is \n {} \n", departmentDTO.toString());
    Department department = departmentDTO.toConvertEntity();
    logger.trace("department is \n {} \n", department.toString());
    Long id = departmentService.create(department, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success", department.toConvertDTO());
  }

  @ApiOperation(value="更新部门", notes="更新部门")
  @ApiImplicitParams({
    @ApiImplicitParam(name="departmentId", value="部门ID", required=true, dataType="Long", paramType="path"),
    @ApiImplicitParam(name="department", value="部门", required=true, dataType="Department", paramType="body")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{departmentId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> updateDepartment(
      @PathVariable("departmentId") Long departmentId,
      @RequestBody DepartmentDTO departmentDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("departmentId is {}, departmentDTO is \n {} \n", departmentId.toString(), departmentDTO.toString());
    Department department = departmentDTO.toConvertEntity();
    department.setId(departmentId);
    logger.trace("department is \n {} \n", department.toString());
    departmentService.update(department, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="删除部门", notes="删除部门")
  @ApiImplicitParams({
    @ApiImplicitParam(name="departmentId", value="部门ID", required=true, dataType="Long", paramType="path"),
    @ApiImplicitParam(name = "version", value = "版本信息", required = true, dataType = "Integer", paramType = "path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{departmentId}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> deleteDepartment(
      @PathVariable("departmentId") Long departmentId,
      @PathVariable("version") Integer version,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("departmentId is {}\n", departmentId.toString());
    departmentService.delete(departmentId, version, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="按ID获取部门", notes="按ID获取部门")
  @ApiImplicitParams({
    @ApiImplicitParam(name="departmentId", value="部门ID", required=true, dataType="Long", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{departmentId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<DepartmentDTO> getDepartment(@PathVariable("departmentId") Long departmentId) throws Exception {
    logger.trace("departmentId is {} \n", departmentId.toString());
    Department department = departmentService.getById(departmentId);
    logger.trace("department is \n {} \n", department.toString());
    DepartmentDTO departmentDTO = department.toConvertDTO();
    logger.trace("departmentDTO is \n {} \n", departmentDTO.toString());
    return new ResponseBody<>(ReplyBizStatus.OK, "success", departmentDTO);
  }

  @ApiOperation(value="查询部门列表", notes="查询部门列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name="departmentQuery", value="部门Query", required=true, dataType="Query", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/query", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<List<DepartmentDTO>> listDepartment(@RequestBody() Query<DepartmentFilter> query) throws Exception {
    logger.trace("department query is {} \n", query.toString());
    List<Department> departments = departmentService.list(query);
    logger.trace("department list is [\n {} \n]", Joiner.on("\t\n").join(departments));
    return new ResponseBody<>(ReplyBizStatus.OK, "success",
        departments.stream().map(department -> department.toConvertDTO()).collect(Collectors.toList()));
  }
}