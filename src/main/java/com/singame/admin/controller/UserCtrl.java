package com.singame.admin.controller;

import com.singame.admin.common.ReplyBizStatus;
import com.singame.admin.common.ResponseBody;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/v1/users")
public class UserCtrl {
  private static Logger logger = LoggerFactory.getLogger(UserCtrl.class);
  @Autowired
  private UserService userService;

  @ApiOperation(value="获取用户自己的信息", notes="获取用户自己的信息")
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=UserDTO.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/me", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<UserDTO> getUser(@RequestAttribute(
      ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    return new ResponseBody<UserDTO>(ReplyBizStatus.OK, "success", userAuth.getUser());
  }

  @ApiOperation(value = "创建用户", notes = "创建用户")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User", paramType = "body")
  })
  @ApiResponses({
    @ApiResponse(code = 200, message = "Success", response = ResponseBody.class),
    @ApiResponse(code = 400, message = "Bad Request", response = ResponseBody.class),
    @ApiResponse(code = 500, message = "Server Interal Error", response = ResponseBody.class)
  })
  @RequestMapping(value="", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<UserDTO> createUser(
      @RequestBody UserDTO userDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    User user = userDTO.toConvertEntity();
    Long id = userService.create(user, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success", user.toConvertDTO());
  }

  @ApiOperation(value="修改用户信息", notes="修改用户信息")
  @ApiImplicitParams({
    @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="Long", paramType="path"),
    @ApiImplicitParam(name="user", value="用户信息", required=true, dataType="User", paramType="body")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{userId}/update", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> updateUser(
      @PathParam("userId") Long userId,
      @RequestBody UserDTO userDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    logger.trace("userAuthDTO is \n {} \n", userAuth.getUser().toString());
    User user = userDTO.toConvertEntity();
    user.setId(userId);
    User operator = userAuth.getUser().toConvertEntity();
    logger.trace("operatory is \n {} \n", user.toString());
    userService.update(user, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="用户ID查询用户详情", notes="用户ID查询用户详情")
  @ApiImplicitParams({
    @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="Long", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{userId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<UserDTO> getUserById(@PathParam("userId") Long userId) throws Exception {
    User user = userService.getById(userId);
    return new ResponseBody<UserDTO>(ReplyBizStatus.OK, "success", user.toConvertDTO());
  }

  @ApiOperation(value="用户编码查询用户详情", notes="用户编码查询用户详情")
  @ApiImplicitParams({
    @ApiImplicitParam(name="userCode", value="用户编码", required=true, dataType="Long", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/code/{userCode}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<UserDTO> getUserByCode(@PathParam("userCode") String userCode) throws Exception {
    User user = userService.getByCode(userCode);
    return new ResponseBody<UserDTO>(ReplyBizStatus.OK, "success", user.toConvertDTO());
  }

  @ApiOperation(value="查询用户列表", notes="查询用户列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name="query", value="查询条件", required=true, dataType="Query", paramType="body")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/query", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<List<UserDTO>> listUser(@RequestBody Query<UserFilter> query) throws Exception {
    List<User> users = userService.list(query);
    return new ResponseBody<List<UserDTO>>(ReplyBizStatus.OK, "success",
      users.stream().map(user -> user.toConvertDTO()).collect(Collectors.toList())
    );
  }


  @ApiOperation(value="冻结用户", notes="冻结用户")
  @ApiImplicitParams({
    @ApiImplicitParam(name="userId", value="用户Id", required=true, dataType="Long", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{userId}/freeze", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> freezeUser(
      @PathParam("userId") Long userId,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    userService.freeze(userId, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="解冻用户", notes="解冻用户")
  @ApiImplicitParams({
    @ApiImplicitParam(name="userId", value="用户Id", required=true, dataType="Long", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{userId}/unfreeze", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<String> unfreezeUser(
      @PathParam("userId") Long userId,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    userService.unfreeze(userId, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="禁用用户", notes="禁用用户")
  @ApiImplicitParams({
    @ApiImplicitParam(name="userId", value="用户Id", required=true, dataType="Long", paramType="path")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{userId}/disable", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> udisableUser(
      @PathParam("userId") Long userId,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth)
    throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    userService.disable(userId, operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="分配角色", notes="分配角色")
  @ApiImplicitParams({
    @ApiImplicitParam(name="userId", value="用户ID", required=true, dataType="Long", paramType="path"),
    @ApiImplicitParam(name="version", value="分配版本", required=true, dataType="Integer", paramType="path"),
    @ApiImplicitParam(name="roleIdList", value="角色Id列表", required=true, allowMultiple=true, dataType="Long", paramType="body")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="/{userId}/roles/dispatch", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> dispatchRoles(
      @PathParam("userId") Long userId,
      @RequestBody DispatchUserRoleDTO userRoleDTO,
      @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    User operator = userAuth.getUser().toConvertEntity();
    userService.dispatchRoles(userId, userRoleDTO.getRoleIdList(), userRoleDTO.getVersion(), operator);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }
}