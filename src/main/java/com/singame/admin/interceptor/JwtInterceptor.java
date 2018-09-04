package com.singame.admin.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.singame.admin.exception.UnauthorizedException;
import com.singame.admin.common.ReqAttrKey;
import com.singame.admin.dto.UserAuthDTO;
import com.singame.admin.utils.JwtUtil;
import com.google.common.base.Strings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
  @Resource
  private RedisTemplate<String, UserAuthDTO> redisTemplate;
  @Value("${jwt.header.token.key}")
  private String JWT_TOKEN_KEY;
  @Value("${jwt.prefix}")
  private String JWT_HEADER_PREFIX;
  @Value("${jwt.sceret}")
  private String JWT_SECRET;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse reponse, Object handle) throws Exception {
    if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
      return true;
    }
    log.debug("拦截器1执行-----preHandle");
    String authHeader = request.getHeader(JWT_TOKEN_KEY);
    if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith(JWT_HEADER_PREFIX)) {
      throw new UnauthorizedException();
    }
    String token = authHeader.substring(JWT_HEADER_PREFIX.length() + 1);
    try {
      if (!JwtUtil.validateToken(token, JWT_SECRET)) {
        throw new UnauthorizedException("token 过期");
      }
      String sessionId = JwtUtil.getSessionId(token, JWT_SECRET);
      UserAuthDTO userAuth = redisTemplate.opsForValue().get(sessionId);
      if (userAuth == null) {
        throw new UnauthorizedException("token 过期");
      }
      request.setAttribute(ReqAttrKey.REQ_USER_AUTH_KEY, userAuth);
    } catch (Exception e) {
      log.error("error when parse token ======" + e.getMessage());
      throw new UnauthorizedException("token 过期");
    }
    return true;
  }
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    log.debug("拦截器1执行-----postHandle");
  }
  
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    log.debug("拦截器1执行-----afterCompletion");
  }

}