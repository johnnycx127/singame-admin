package com.singame.admin.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.singame.admin.common.ReplyBizStatus;
import com.singame.admin.common.ReqAttrKey;
import com.singame.admin.common.ResponseBody;
import com.singame.admin.domain.Permission;
import com.singame.admin.domain.Role;
import com.singame.admin.domain.User;
import com.singame.admin.dto.LoginDTO;
import com.singame.admin.dto.TokenRespDTO;
import com.singame.admin.dto.UserAuthDTO;
import com.singame.admin.exception.BadRequestException;
import com.singame.admin.exception.InternalServerException;
import com.singame.admin.exception.NotFoundException;
import com.singame.admin.exception.UnauthorizedException;
import com.singame.admin.query.Query;
import com.singame.admin.query.filter.PermissionFilter;
import com.singame.admin.query.filter.RoleFilter;
import com.singame.admin.service.PermissionService;
import com.singame.admin.service.RoleService;
import com.singame.admin.service.UserService;
import com.singame.admin.utils.JwtUtil;
import com.singame.admin.utils.SignatureUtil;
import com.google.common.base.Strings;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/auth")
public class AuthCtrl {
  private Logger logger = LoggerFactory.getLogger(AuthCtrl.class);

  @Resource
  private RedisTemplate<String, UserAuthDTO> redisTemplate;
  @Autowired
  private UserService userService;
  @Autowired
  private PermissionService permissionService;
  @Autowired
  private RoleService roleService;

  @Value("${jwt.header}")
  private String jwtHeader;
  @Value("${jwt.prefix}")
  private String jwtHeaderPrefix;
  @Value("${jwt.sceret}")
  private String jwtSceret;
  @Value("${jwt.expired-time}")
  private Long jwtExpiredTime;
  @Value("${jwt.expired-week-time}")
  private Long jwtExpiredWeekTime;


  @ApiOperation(value="登录", notes="登录")
  @ApiImplicitParams({
    @ApiImplicitParam(name="loginReq", value="登录信息", required=true, dataType="LoginReq", paramType="body")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="signin", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<TokenRespDTO> login(
      @RequestBody final LoginDTO loginReq,
      HttpServletRequest request) throws BadRequestException, NotFoundException, InternalServerException {
    if (Strings.isNullOrEmpty(loginReq.getCode())) {
      throw new BadRequestException("Invalid login");
    }
    User u = userService.getByCode(loginReq.getCode());
    String psw = u.getPassword();
    if (!BCrypt.checkpw(loginReq.getPassword(), psw)) {
      throw new BadRequestException("密码错误");
    }
    UserAuthDTO userAuth = new UserAuthDTO();
    // set userDTO
    userAuth.setUser(u.toConvertDTO());
    // TODO roleDTO list builder 
    RoleFilter roleFilter = new RoleFilter();
    roleFilter.setUserId(u.getId());
    Query<RoleFilter> roleQuery = new Query<>();
    roleQuery.setFilter(roleFilter);
    List<Role> roles = roleService.list(roleQuery);
    userAuth.setRoleList(roles.stream().map(role -> role.toConvertDTO()).collect(Collectors.toList()));
    // TODO permissionDTO list builder
    Set<Permission> permissionSet = new HashSet<>();
    for (Role role : roles) {
      PermissionFilter pFilter = new PermissionFilter();
      pFilter.setRoleId(role.getId());
      Query<PermissionFilter> permissionQuery = new Query<>();
      permissionQuery.setFilter(pFilter);
      List<Permission> permissions = permissionService.list(permissionQuery);
      permissionSet.addAll(permissions);
    }
    userAuth.setPermissionList(permissionSet.stream().map(p -> p.toConvertDTO()).collect(Collectors.toList()));
    String sessionId = SignatureUtil.sha256(u.getId().toString());
    redisTemplate.opsForValue().set(sessionId, userAuth);
    String token = JwtUtil.createToken(sessionId, jwtSceret, jwtExpiredTime);
    String refreshToken = JwtUtil.getRefreshToken(sessionId, jwtSceret, jwtExpiredWeekTime);
    if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(refreshToken)) {
      throw new InternalServerException();
    }
    return new ResponseBody<>(ReplyBizStatus.OK, "success", new TokenRespDTO(token, refreshToken));
  }
  
  @ApiOperation(value="刷新Token", notes="刷新Token")
  @ApiImplicitParams({
    @ApiImplicitParam(name="refreshToken", value="刷新Token", required=true, dataType="String", paramType="query")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="token/refresh", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<TokenRespDTO> refreshtoken(@RequestParam("refreshToken") final String refreshToken, HttpServletRequest request) throws Exception {
    String newToken = JwtUtil.refreshToken(refreshToken, jwtSceret, jwtExpiredTime);
    if (Strings.isNullOrEmpty(newToken)) {
      throw new UnauthorizedException("token 过期，请重新登录");
    }
    return new ResponseBody<>(ReplyBizStatus.OK, "success", new TokenRespDTO(newToken, refreshToken));
  }

  @ApiOperation(value="注册", notes="注册")
  @ApiImplicitParams({
    @ApiImplicitParam(name="loginReq", value="登录信息", required=true, dataType="LoginReq", paramType="body")
  })
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="signup", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<Long> signup(
      @RequestBody final LoginDTO loginDTO,
      HttpServletRequest request) throws BadRequestException {
    String scretedPass = BCrypt.hashpw(loginDTO.getPassword(), BCrypt.gensalt());
    User u = new User();
    u.setName(loginDTO.getCode());
    u.setPassword(scretedPass);
    Long id = userService.create(u);
    return new ResponseBody<>(ReplyBizStatus.OK, "success", id);
  }

  @ApiOperation(value="登出", notes="登出")
  @ApiResponses({
    @ApiResponse(code=200, message="Success", response=ResponseBody.class),
    @ApiResponse(code=400, message="Bad Request", response=ResponseBody.class),
    @ApiResponse(code=500, message="Server Interal Error", response=ResponseBody.class)
  })
  @RequestMapping(value="logout", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseBody<?> logout(@RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth)
      throws BadRequestException {
    String sessionId = SignatureUtil.sha256(userAuth.getUser().getId().toString());
    redisTemplate.delete(sessionId);
    return new ResponseBody<>(ReplyBizStatus.OK, "success");
  }
}