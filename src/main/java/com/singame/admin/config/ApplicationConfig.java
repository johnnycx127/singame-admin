package com.singame.admin.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.singame.admin.interceptor.JwtInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig extends WebMvcConfigurationSupport {

  @Value("${jwt.header}")
  private String jwtHeaderKey;
  @Value("${jwt.prefix}")
  private String jwtHeaderPrefix;
  @Value("${jwt.sceret}")
  private String jwtSceret;

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
    return new JwtInterceptor(jwtHeaderKey, jwtHeaderPrefix, jwtSceret);
  }
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jwtInterceptor())
        .addPathPatterns("/api/**", "/auth/token/refresh", "/auth/logout")
        .excludePathPatterns("/auth/signin", "/auth/signup");
    super.addInterceptors(registry);
  }
}