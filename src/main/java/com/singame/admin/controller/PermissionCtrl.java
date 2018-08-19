package com.singame.admin.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.singame.admin.common.ReplyBizStatus;
import com.singame.admin.common.ReqAttrKey;
import com.singame.admin.common.ResponseBody;
import com.singame.admin.domain.Permission;
import com.singame.admin.domain.User;
import com.singame.admin.dto.PermissionDTO;
import com.singame.admin.dto.UserAuthDTO;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;
import com.singame.admin.service.PermissionService;

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
@RequestMapping("/api/v1/permissions")
public class PermissionCtrl {
  private static Logger logger = LoggerFactory.getLogger(PermissionCtrl.class);
  @Autowired
  private PermissionService permissionService;

  @ApiOperation(value="新建权限", notes="新建权限")
  @ApiImplicitParams({
    @ApiImplicitParam(name="permission", value="权限", required=true, dataType="Permission", paramType="body")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<PermissionDTO> createPermission(
      @RequestBody PermissionDTO permissionDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("permissionDTO is \n {} \n", permissionDTO.toString());
    Permission permission = permissionDTO.toConvertEntity();
    logger.trace("permission is \n {} \n", permission.toString());
    Long id = permissionService.create(permission, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success", permission.toConvertDTO());
  }

  @ApiOperation(value="更新权限", notes="更新权限")
  @ApiImplicitParams({
    @ApiImplicitParam(name="permissionId", value="权限ID", required=true, dataType="Long", paramType="path"),
    @ApiImplicitParam(name="permission", value="权限", required=true, dataType="Permission", paramType="body")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{permissionId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> updatePermission(
      @PathVariable("permissionId") Long permissionId,
      @RequestBody PermissionDTO permissionDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("permissionId is {}, permissionDTO is \n {} \n", permissionId.toString(), permissionDTO.toString());
    Permission permission = permissionDTO.toConvertEntity();
    permission.setId(permissionId);
    logger.trace("permission is \n {} \n", permission.toString());
    permissionService.update(permission, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="删除权限", notes="删除权限")
  @ApiImplicitParams({
    @ApiImplicitParam(name="permissionId", value="权限ID", required=true, dataType="Long", paramType="path"),
    @ApiImplicitParam(name = "version", value = "版本信息", required = true, dataType = "Integer", paramType = "path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{permissionId}/version/{version}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> deletePermission(
      @PathVariable("permissionId") Long permissionId,
      @PathVariable("version") Integer version,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("permissionId is {}\n", permissionId.toString());
    permissionService.delete(permissionId, version, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="按ID获取权限", notes="按ID获取权限")
  @ApiImplicitParams({
    @ApiImplicitParam(name="permissionId", value="权限ID", required=true, dataType="Long", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{permissionId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<PermissionDTO> getPermissionById(@PathVariable("permissionId") Long permissionId) throws Exception {
    logger.trace("permissionId is {} \n", permissionId.toString());
    Permission permission = permissionService.getById(permissionId);
    logger.trace("permission is \n {} \n", permission.toString());
    PermissionDTO permissionDTO = permission.toConvertDTO();
    logger.trace("permissionDTO is \n {} \n", permissionDTO.toString());
    return new ResponseBody<>(ReplyBizStatus.OK, "success", permissionDTO);
  }

  @ApiOperation(value="按code获取权限", notes="按code获取权限")
  @ApiImplicitParams({
    @ApiImplicitParam(name="permissionCode", value="权限code", required=true, dataType="Long", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/code/{permissionCode}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<PermissionDTO> getPermissionByCode(@PathVariable("permissionCode") String permissionCode) throws Exception {
    logger.trace("permissionCode is {} \n", permissionCode.toString());
    Permission permission = permissionService.getByCode(permissionCode);
    logger.trace("permission is \n {} \n", permission.toString());
    PermissionDTO permissionDTO = permission.toConvertDTO();
    logger.trace("permissionDTO is \n {} \n", permissionDTO.toString());
    return new ResponseBody<>(ReplyBizStatus.OK, "success", permissionDTO);
  }

  @ApiOperation(value="查询权限列表", notes="查询权限列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name="permissionQuery", value="权限Query", required=true, dataType="Query", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/query", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<List<PermissionDTO>> listPermission(@RequestBody() Query<PermissionFilter> query) throws Exception {
    logger.trace("permission query is {} \n", query.toString());
    List<Permission> permissions = permissionService.list(query);
    logger.trace("permission list is [\n {} \n]", Joiner.on("\t\n").join(permissions));
    return new ResponseBody<>(ReplyBizStatus.OK, "success",
        permissions.stream().map(permission -> permission.toConvertDTO()).collect(Collectors.toList()));
  }
}