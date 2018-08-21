package com.singame.admin.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.singame.admin.interceptor.JwtInterceptor;
import com.singame.admin.interceptor.PermissionInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig extends WebMvcConfigurationSupport {

  @Bean
  public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
    // 设置序列化
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
            Object.class);
    ObjectMapper om = new ObjectMapper();
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    // 配置redisTemplate
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
    redisTemplate.setConnectionFactory(lettuceConnectionFactory);
    RedisSerializer<?> stringSerializer = new StringRedisSerializer();
    redisTemplate.setKeySerializer(stringSerializer);// key序列化
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
    redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
  
  @Bean
  public JwtInterceptor jwtInterceptor() {
    return new JwtInterceptor();
  }

  @Bean PermissionInterceptor permissionInterceptor() {
    return new PermissionInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jwtInterceptor())
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns(
              "api/v1/auth/signin", "api/v1/auth/signup");
    registry.addInterceptor(permissionInterceptor())
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns("api/v1/auth/**");
            
    super.addInterceptors(registry);
  }

   /**
     * 配置servlet处理
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
     * 需要重新指定静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}