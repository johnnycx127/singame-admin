package com.singame.admin.controller;

import com.singame.admin.common.ReplyBizStatus;
import com.singame.admin.common.Reply;
import com.singame.admin.domain.User;
import com.singame.admin.dto.DispatchUserRoleDTO;
import com.singame.admin.dto.UserAuthDTO;
import com.singame.admin.dto.UserDTO;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.UserFilter;
import com.singame.admin.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import com.singame.admin.common.ReqAttrKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/v1/users")
public class UserCtrl {

  @Autowired
  private UserService userService;

  @ApiOperation(value="获取用户自己的信息", notes="获取用户自己的信息")
  @RequestMapping(value="/me", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<UserDTO> getUser(
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth)
      throws Exception {
    return new Reply<UserDTO>(ReplyBizStatus.OK, "success", userAuth.getUser());
  }

  @ApiOperation(value = "创建用户", notes = "创建用户")
  @RequestMapping(value="", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<UserDTO> createUser(
      @ApiParam @RequestBody UserDTO userDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    User user = userDTO.toConvertEntity();
    Long id = userService.create(user, operator);
    return new Reply<>(ReplyBizStatus.OK, "success", user.toConvertDTO());
  }

  @ApiOperation(value="修改用户信息", notes="修改用户信息")
  @RequestMapping(value="/{userId}/update", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> updateUser(
      @ApiParam @PathParam("userId") Long userId,
      @ApiParam @RequestBody UserDTO userDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    log.trace("userAuthDTO is \n {} \n", userAuth.getUser().toString());
    User user = userDTO.toConvertEntity();
    user.setId(userId);
    User operator = userAuth.getUser().toConvertEntity();
    log.trace("operatory is \n {} \n", user.toString());
    userService.update(user, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="用户ID查询用户详情", notes="用户ID查询用户详情")
  @RequestMapping(value="/{userId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<UserDTO> getUserById(@ApiParam @PathParam("userId") Long userId) throws Exception {
    User user = userService.getById(userId);
    return new Reply<UserDTO>(ReplyBizStatus.OK, "success", user.toConvertDTO());
  }

  @ApiOperation(value="用户编码查询用户详情", notes="用户编码查询用户详情")
  @RequestMapping(value="/code/{userCode}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<UserDTO> getUserByCode(@ApiParam @PathParam("userCode") String userCode) throws Exception {
    User user = userService.getByCode(userCode);
    return new Reply<UserDTO>(ReplyBizStatus.OK, "success", user.toConvertDTO());
  }

  @ApiOperation(value="查询用户列表", notes="查询用户列表")
  @RequestMapping(value="/query", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<List<UserDTO>> listUser(@ApiParam @RequestBody Query<UserFilter> query) throws Exception {
    List<User> users = userService.list(query);
    return new Reply<List<UserDTO>>(ReplyBizStatus.OK, "success",
      users.stream().map(user -> user.toConvertDTO()).collect(Collectors.toList())
    );
  }


  @ApiOperation(value="冻结用户", notes="冻结用户")
  @RequestMapping(value="/{userId}/freeze", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> freezeUser(
      @ApiParam @PathParam("userId") Long userId,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    userService.freeze(userId, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="解冻用户", notes="解冻用户")
  @RequestMapping(value="/{userId}/unfreeze", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<String> unfreezeUser(
      @ApiParam @PathParam("userId") Long userId,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    userService.unfreeze(userId, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="禁用用户", notes="禁用用户")
  @RequestMapping(value="/{userId}/disable", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> udisableUser(
      @ApiParam @PathParam("userId") Long userId,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth)
    throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    userService.disable(userId, operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="分配角色", notes="分配角色")
  @RequestMapping(value="/{userId}/roles/dispatch", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> dispatchRoles(
      @ApiParam @PathParam("userId") Long userId,
      @ApiParam @RequestBody DispatchUserRoleDTO userRoleDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    userService.dispatchRoles(userId, userRoleDTO.getRoleIdList(), userRoleDTO.getVersion(), operator);
    return new Reply<>(ReplyBizStatus.OK, "success");
  }
}