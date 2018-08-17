package com.singame.admin.controller;

import javax.servlet.http.HttpServletRequest;

import com.singame.admin.common.ReplyBizStatus;
import com.singame.admin.common.ResponseBody;
import com.singame.admin.domain.User;
import com.singame.admin.dto.DispatchUserRoleDTO;
import com.singame.admin.dto.UserAuthDTO;
import com.singame.admin.dto.UserDTO;
import com.singame.admin.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

  @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Success", response = UserDTO.class),
    @ApiResponse(code = 500, message = "Server Interal Error", response = ResponseBody.class)
  })
  @RequestMapping(value = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<UserDTO> getUser(HttpServletRequest request) throws Exception {
    UserAuthDTO userAuth = (UserAuthDTO) request.getAttribute("auth");
    return new ResponseBody<UserDTO>(ReplyBizStatus.OK, "success", userAuth.getUser());
  }

  @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Success", response = ResponseBody.class),
    @ApiResponse(code = 400, message = "Bad Request", response = ResponseBody.class),
    @ApiResponse(code = 500, message = "Server Interal Error", response = ResponseBody.class)
  })
  @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> updateUser(HttpServletRequest request) throws Exception {
    UserAuthDTO userAuth = (UserAuthDTO) request.getAttribute("auth");
    logger.debug("userAuthDTO is \n {} \n", userAuth.getUser().toString());
    User user = userAuth.getUser().toConvertEntity();
    logger.debug("user is \n {} \n", user.toString());
    userService.update(user);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }


  @ApiOperation(value = "冻结用户", notes = "冻结用户")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Success", response = ResponseBody.class),
    @ApiResponse(code = 400, message = "Bad Request", response = ResponseBody.class),
    @ApiResponse(code = 500, message = "Server Interal Error", response = ResponseBody.class)
  })
  @RequestMapping(value = "/freeze", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> freezeUser(HttpServletRequest request) throws Exception {
    UserAuthDTO userAuth = (UserAuthDTO) request.getAttribute("auth");
    User user = userAuth.getUser().toConvertEntity();
    userService.freeze(user.getId());
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value = "解冻用户", notes = "解冻用户")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Success", response = ResponseBody.class),
    @ApiResponse(code = 400, message = "Bad Request", response = ResponseBody.class),
    @ApiResponse(code = 500, message = "Server Interal Error", response = ResponseBody.class)
  })
  @RequestMapping(value = "/unfreeze", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<String> unfreezeUser(HttpServletRequest request) throws Exception {
    UserAuthDTO userAuth = (UserAuthDTO) request.getAttribute("auth");
    User user = userAuth.getUser().toConvertEntity();
    userService.unfreeze(user.getId());
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value = "禁用用户", notes = "禁用用户")
  @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = ResponseBody.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ResponseBody.class),
      @ApiResponse(code = 500, message = "Server Interal Error", response = ResponseBody.class) })
  @RequestMapping(value = "/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> udisableUser(HttpServletRequest request) throws Exception {
    UserAuthDTO userAuth = (UserAuthDTO) request.getAttribute("auth");
    User user = userAuth.getUser().toConvertEntity();
    userService.disable(user.getId());
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value = "分配角色", notes = "分配角色")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "roleIdList", value = "角色Id列表", required = true, allowMultiple = true, dataType = "Long", paramType = "body") })
  @ApiResponses({ @ApiResponse(code = 200, message = "Success", response = ResponseBody.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ResponseBody.class),
      @ApiResponse(code = 500, message = "Server Interal Error", response = ResponseBody.class) })
  @RequestMapping(value = "/roles/dispatch", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> dispatchRoles(@RequestBody DispatchUserRoleDTO userRoleDTO, HttpServletRequest request) throws Exception {
    UserAuthDTO userAuth = (UserAuthDTO) request.getAttribute("auth");
    User user = userAuth.getUser().toConvertEntity();
    userService.dispatchRoles(user.getId(), userRoleDTO.getRoleIdList());
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }
}