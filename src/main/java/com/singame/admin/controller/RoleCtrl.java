package com.singame.admin.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.singame.admin.common.ReplyBizStatus;
import com.singame.admin.common.Reply;
import com.singame.admin.domain.Role;
import com.singame.admin.domain.User;
import com.singame.admin.dto.DispatchRolePermissionDTO;
import com.singame.admin.dto.RoleDTO;
import com.singame.admin.dto.UserAuthDTO;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.RoleFilter;
import com.singame.admin.service.RoleService;
import com.singame.admin.common.ReqAttrKey;

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
@RequestMapping("/api/v1/roles")
public class RoleCtrl {
  private static Logger logger = LoggerFactory.getLogger(RoleCtrl.class);
  @Autowired
  private RoleService roleService;

  @ApiOperation(value="新建角色", notes="新建角色")
  @RequestMapping(value="", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<RoleDTO> createRole(
      @ApiParam @RequestBody RoleDTO roleDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("roleDTO is \n {} \n", roleDTO.toString());
    Role role = roleDTO.toConvertEntity();
    logger.trace("role is \n {} \n", role.toString());
    Long id = roleService.create(role, operator);
    return new Reply<>(ReplyBizStatus.OK, "success", role.toConvertDTO());
  }

  @ApiOperation(value="更新角色", notes="更新角色")
  @RequestMapping(value="/{roleId}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> updateRole(
      @ApiParam @PathVariable("roleId") Long roleId,
      @ApiParam @RequestBody RoleDTO roleDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("roleId is {}, roleDTO is \n {} \n",roleId.toString() , roleDTO.toString());
    Role role = roleDTO.toConvertEntity();
    role.setId(roleId);
    logger.trace("role is \n {} \n", role.toString());
    roleService.update(role, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="删除角色", notes="删除角色")
  @RequestMapping(value="/{roleId}/version/{version}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> deleteRole(
      @ApiParam @PathVariable("roleId") Long roleId,
      @ApiParam @PathVariable("version") Integer version,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("roleId is {}\n", roleId.toString());
    roleService.delete(roleId, version, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="按ID获取角色", notes="按ID获取角色")
  @RequestMapping(value="/{roleId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<RoleDTO> getRole(
      @ApiParam @PathVariable("roleId") Long roleId) throws Exception {
    logger.trace("roleId is {} \n", roleId.toString());
    Role role = roleService.getById(roleId);
    logger.trace("role is \n {} \n", role.toString());
    RoleDTO roleDTO = role.toConvertDTO();
    logger.trace("roleDTO is \n {} \n", roleDTO.toString());
    return new Reply<>(ReplyBizStatus.OK, "success", roleDTO);
  }

  @ApiOperation(value="查询角色列表", notes="查询角色列表")
  @RequestMapping(value="/query", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<List<RoleDTO>> listRole(
      @ApiParam @RequestBody() Query<RoleFilter> query) throws Exception {
    logger.trace("role query is {} \n", query.toString());
    List<Role> roles = roleService.list(query);
    logger.trace("role list is [\n {} \n]", Joiner.on("\t\n").join(roles));
    return new Reply<>(ReplyBizStatus.OK, "success",
      roles.stream().map(role -> role.toConvertDTO()).collect(Collectors.toList()));
  }

  @ApiOperation(value="分配权限", notes="分配权限")
  @RequestMapping(value="/{roleId}/permissions/dispatch", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> dispatchPermission(
      @ApiParam @PathVariable("roleId") Long roleId,
      @ApiParam @RequestBody DispatchRolePermissionDTO rolePermissionDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth)throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    roleService.dispatchPermission(
      roleId,
      rolePermissionDTO.getPermissionIdList(), 
      rolePermissionDTO.getVersion(), operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }
}