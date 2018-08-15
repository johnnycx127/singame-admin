package com.singame.admin.controller;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.singame.admin.domain.User;
import com.singame.admin.dto.PermissionTokenDTO;
import com.singame.admin.dto.UserDTO;
import com.singame.admin.exception.BadRequestException;
import com.singame.admin.exception.UnauthorizedException;
import com.singame.admin.service.PermissionService;
import com.singame.admin.service.UserService;
import com.singame.admin.utils.JwtUtil;
import com.singame.admin.utils.SignatureUtil;
import com.google.common.base.Strings;

import org.joda.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthCtrl {
  private Logger logger = LoggerFactory.getLogger(AuthCtrl.class);

  @Resource
  private RedisTemplate<String, PermissionTokenDTO> redisTemplate;

  @Autowired
  private UserService userService;

  @Autowired
  private PermissionService permissionService;

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

  private static class TokenObj {
    public String token;
    public String refreshToken;
  }

  @SuppressWarnings("unused")
  private static class SigninResponse {
    public String token;
    public String refreshToken;

    public SigninResponse(final String token, final String refreshToken) {
      this.token = token;
      this.refreshToken = refreshToken;
    }
  }

  @RequestMapping(value = "signin", method = RequestMethod.POST)
  public ResponseEntity<SigninResponse> login(@RequestBody final UserDTO login, HttpServletRequest request) throws BadRequestException {
    if (Strings.isNullOrEmpty(login.getName())) {
      throw new BadRequestException("Invalid login");
    }
    String token = "";
    String refreshToken = "";
    try {
      User u = userService.getByName(login.getName());
      String psw = u.getPassword();
      if (!BCrypt.checkpw(login.getPassword(), psw)) {
        throw new ServletException("密码错误");
      }
      PermissionTokenDTO permissionObj = permissionService.getpermissionlist(u.getId());
      String signedId = SignatureUtil.sha256(u.getId().toString());
      redisTemplate.opsForValue().set(signedId, permissionObj);
      token = JwtUtil.createToken(signedId, jwtSceret, jwtExpiredTime);
      refreshToken = JwtUtil.getRefreshToken(signedId, jwtSceret, jwtExpiredWeekTime);
    } catch (ServletException e) {
      logger.error(e.getMessage());
    }
    if (!Strings.isNullOrEmpty(token) && !Strings.isNullOrEmpty(refreshToken)) {
      return new ResponseEntity<SigninResponse>(new SigninResponse(token, refreshToken), HttpStatus.OK);
    }
    return null;
  }
  
  @RequestMapping(value = "token/refresh", method = RequestMethod.POST)
  public ResponseEntity<SigninResponse> refreshtoken(@RequestBody final TokenObj tokenObj, HttpServletRequest request) throws Exception {
    String newToken = JwtUtil.refreshToken(tokenObj.refreshToken, jwtSceret, jwtExpiredTime);
    if (Strings.isNullOrEmpty(newToken)) {
      throw new UnauthorizedException("token 过期，请重新登录");
    }
    logger.debug("new token: " + newToken);
    return new ResponseEntity<SigninResponse>(new SigninResponse(newToken, tokenObj.refreshToken), HttpStatus.OK);
  }
  

  @RequestMapping(value = "signup", method = RequestMethod.POST)
  public ResponseEntity<Long> register(@RequestBody final UserDTO userDTO, HttpServletRequest request) throws BadRequestException {
    String scretedPass = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
    UserDTO u = new UserDTO();
    u.setName(userDTO.getName());
    u.setPassword(scretedPass);
    u.setCreatedBy(userDTO.getCreatedBy());
    u.setCreatedAt(LocalDateTime.now());
    Long id = userService.create(u);
    logger.debug("redis template is " + redisTemplate.toString());
    return new ResponseEntity<Long>(id, HttpStatus.OK);
  }

  @RequestMapping(value = "logout", method = RequestMethod.GET)
  public ResponseEntity<String> logout(HttpServletRequest request)
      throws BadRequestException {
    try {
      String authHeader = request.getHeader(jwtHeader);
      if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith(jwtHeaderPrefix)) {
        throw new BadRequestException("非法签名");
      }
      String token = authHeader.substring(jwtHeaderPrefix.length() + 1);
      PermissionTokenDTO permissionObj = redisTemplate.opsForValue().get(JwtUtil.getSessionId(token, jwtSceret));
      if (permissionObj == null) {
        throw new BadRequestException("非法签名");
      }
      redisTemplate.delete(JwtUtil.getSessionId(token, jwtSceret));
    } catch (Exception e) {
      throw new BadRequestException("非法签名");
    }
    
    return new ResponseEntity<String>("success", HttpStatus.OK);
  }
}