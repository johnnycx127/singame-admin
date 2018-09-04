package com.singame.admin.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.singame.admin.common.ReqAttrKey;
import com.singame.admin.dto.UserAuthDTO;
import com.singame.admin.exception.AccessForbiddenException;
import com.singame.admin.vo.PermissionAction;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.pattern.PathPatternParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {
  @Resource
  private RedisTemplate<String, UserAuthDTO> redisTemplate;

  private static PermissionAction getPermissionAction(String url, String httpMethod) {
    if (HttpMethod.GET.matches(httpMethod)) {
      return PermissionAction.READ;
    } else if (HttpMethod.PUT.matches(httpMethod)) {
      return PermissionAction.WRITE;
    } else if (HttpMethod.DELETE.matches(httpMethod)) {
      return PermissionAction.REMOVE;
    } else if (HttpMethod.POST.matches(httpMethod)) {
      String[] urlItems = url.split("/");
      if ("query".equals(urlItems[urlItems.length - 1])) {
        return PermissionAction.READ;
      } else {
        return PermissionAction.WRITE;
      }
    } else {
      return PermissionAction.UNKNOW;
    }
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse reponse, Object handle) throws Exception {
    if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
      return true;
    }
    String url = request.getServletPath();
    UserAuthDTO userAuthDTO = (UserAuthDTO) request.getAttribute(ReqAttrKey.REQ_USER_AUTH_KEY);
    PathContainer pathContainer = PathContainer.parsePath(url);
    PathPatternParser ppp = new PathPatternParser();
    long matchCount = userAuthDTO.getPermissionList()
                                 .stream()
                                 .filter(
                                   permission -> ppp.parse(permission.getResource()).matches(pathContainer) &&
                                   getPermissionAction(url, request.getMethod()) == permission.getAction())
                                 .count();
    if (matchCount == 0) {
      throw new AccessForbiddenException();
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