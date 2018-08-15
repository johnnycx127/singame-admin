package com.singame.admin.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.singame.admin.dto.PermissionTokenDTO;
import com.singame.admin.exception.UnauthorizedException;
import com.singame.admin.utils.JwtUtil;
import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class JwtInterceptor implements HandlerInterceptor {
  private Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

  @Resource
  private RedisTemplate<String, PermissionTokenDTO> redisTemplate;

  private String jwtHeader;
  private String jwtHeaderPrefix;
  private String jwtSceret;

  public JwtInterceptor(String jwtHeaderKey, String jwtHeaderPrefix, String jwtSceret) {
    this.jwtHeader = jwtHeaderKey;
    this.jwtHeaderPrefix = jwtHeaderPrefix;
    this.jwtSceret = jwtSceret;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse reponse, Object handle) throws Exception {
    logger.debug("拦截器1执行-----preHandle");
    String authHeader = request.getHeader(jwtHeader);
    if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith(jwtHeaderPrefix)) {
      throw new UnauthorizedException();
    }
    String token = authHeader.substring(jwtHeaderPrefix.length() + 1);
    try {
      if (!JwtUtil.validateToken(token, jwtSceret)) {
        throw new UnauthorizedException("token 过期");
      }
      String userId = JwtUtil.getSessionId(token, jwtSceret);
      PermissionTokenDTO tokenObj = redisTemplate.opsForValue().get(userId);
      if (tokenObj == null) {
        throw new UnauthorizedException("token 过期");
      }
      request.setAttribute("auth", tokenObj);
    } catch (Exception e) {
      logger.error("error when parse token ======" + e.getMessage());
      throw new UnauthorizedException("token 过期");
    }
    return true;
  }
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    logger.debug("拦截器1执行-----postHandle");
  }
  
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    logger.debug("拦截器1执行-----afterCompletion");
  }

}