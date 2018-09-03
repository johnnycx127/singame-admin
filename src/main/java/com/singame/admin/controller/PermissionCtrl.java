package com.singame.admin.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.singame.admin.common.ReplyBizStatus;
import com.singame.admin.common.ReqAttrKey;
import com.singame.admin.common.Reply;
import com.singame.admin.domain.Permission;
import com.singame.admin.domain.User;
import com.singame.admin.dto.PermissionDTO;
import com.singame.admin.dto.UserAuthDTO;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;
import com.singame.admin.service.PermissionService;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionCtrl {

  @Autowired
  private PermissionService permissionService;

  @ApiOperation(value="新建权限", notes="新建权限")
  @RequestMapping(value="", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<PermissionDTO> createPermission(
      @ApiParam @RequestBody PermissionDTO permissionDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    log.trace("permissionDTO is \n {} \n", permissionDTO.toString());
    Permission permission = permissionDTO.toConvertEntity();
    log.trace("permission is \n {} \n", permission.toString());
    Long id = permissionService.create(permission, operator);
    return new Reply<>(ReplyBizStatus.OK, "success", permission.toConvertDTO());
  }

  @ApiOperation(value="更新权限", notes="更新权限")
  @RequestMapping(value="/{permissionId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> updatePermission(
      @ApiParam @PathVariable("permissionId") Long permissionId,
      @ApiParam @RequestBody PermissionDTO permissionDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    log.trace("permissionId is {}, permissionDTO is \n {} \n", permissionId.toString(), permissionDTO.toString());
    Permission permission = permissionDTO.toConvertEntity();
    permission.setId(permissionId);
    log.trace("permission is \n {} \n", permission.toString());
    permissionService.update(permission, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="删除权限", notes="删除权限")
  @RequestMapping(value="/{permissionId}/version/{version}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> deletePermission(
      @ApiParam @PathVariable("permissionId") Long permissionId,
      @ApiParam @PathVariable("version") Integer version,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    log.trace("permissionId is {}\n", permissionId.toString());
    permissionService.delete(permissionId, version, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="按ID获取权限", notes="按ID获取权限")
  @RequestMapping(value="/{permissionId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<PermissionDTO> getPermissionById(
      @ApiParam @PathVariable("permissionId") Long permissionId) throws Exception {
    log.trace("permissionId is {} \n", permissionId.toString());
    Permission permission = permissionService.getById(permissionId);
    log.trace("permission is \n {} \n", permission.toString());
    PermissionDTO permissionDTO = permission.toConvertDTO();
    log.trace("permissionDTO is \n {} \n", permissionDTO.toString());
    return new Reply<>(ReplyBizStatus.OK, "success", permissionDTO);
  }

  @ApiOperation(value="按code获取权限", notes="按code获取权限")
  @RequestMapping(value="/code/{permissionCode}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<PermissionDTO> getPermissionByCode(
      @ApiParam @PathVariable("permissionCode") String permissionCode) throws Exception {
    log.trace("permissionCode is {} \n", permissionCode.toString());
    Permission permission = permissionService.getByCode(permissionCode);
    log.trace("permission is \n {} \n", permission.toString());
    PermissionDTO permissionDTO = permission.toConvertDTO();
    log.trace("permissionDTO is \n {} \n", permissionDTO.toString());
    return new Reply<>(ReplyBizStatus.OK, "success", permissionDTO);
  }

  @ApiOperation(value="查询权限列表", notes="查询权限列表")
  @RequestMapping(value="/query", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<List<PermissionDTO>> listPermission(
      @ApiParam @RequestBody Query<PermissionFilter> query) throws Exception {
    log.trace("permission query is {} \n", query.toString());
    List<Permission> permissions = permissionService.list(query);
    log.trace("permission list is [\n {} \n]", Joiner.on("\t\n").join(permissions));
    return new Reply<>(ReplyBizStatus.OK, "success",
        permissions.stream().map(permission -> permission.toConvertDTO()).collect(Collectors.toList()));
  }
}