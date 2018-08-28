package com.singame.admin.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.singame.admin.dto.UserAuthDTO;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final ArrayList<ResponseMessage> defaultResp = newArrayList(
    new ResponseMessageBuilder()
      .code(500)
      .message("interal error")
      .build(),
    new ResponseMessageBuilder()
      .code(400)
      .message("bad request")
      .build(),
    new ResponseMessageBuilder()
      .code(401)
      .message("invalid token")
      .build(),
    new ResponseMessageBuilder()
      .code(403)
      .message("access forbidden")
      .build(),
    new ResponseMessageBuilder()
      .code(404)
      .message("not found")
      .build()
  );

  @Bean
  public Docket api() {
    // 添加head参数end
    return new Docket(DocumentationType.SWAGGER_2)
                    .ignoredParameterTypes(UserAuthDTO.class)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.singame.admin.controller"))
                    .paths(PathSelectors.any())
                    .build()
                    .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                    .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                    .apiInfo(apiInfo())
                    .globalResponseMessage(RequestMethod.GET, defaultResp)
                    .globalResponseMessage(RequestMethod.POST, defaultResp)
                    .globalResponseMessage(RequestMethod.PUT, defaultResp)
                    .globalResponseMessage(RequestMethod.DELETE, defaultResp)
                    .securityContexts(Arrays.asList(securityContext()))
				            .securitySchemes(Arrays.asList(apiKey()));
  }
  
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
                .title("singame admin api")
                .description("singame admin api")
                .termsOfServiceUrl("")
                .version("1.0")
                .contact(new Contact("johnycx", "", "johnnycx127@gmail.com"))
                .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("apiKey", "Authorization", "header");
  }

  private List<SecurityReference> defaultAuth() {
      AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
      AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
      authorizationScopes[0] = authorizationScope;
      return Collections.singletonList(new SecurityReference("apiKey", authorizationScopes));
  }

  private SecurityContext securityContext() {
      return SecurityContext.builder()
              .securityReferences(defaultAuth())
              .forPaths(PathSelectors.regex("/api/*"))
              .build();
  }

  @Bean
  public SecurityConfiguration security() {
      return SecurityConfigurationBuilder.builder()
              .clientId("api-client-id")
              .clientSecret("api-client-secret")
              .realm("api-realm")
              .appName("api-app")
              .scopeSeparator(",")
              .additionalQueryStringParams(null)
              .useBasicAuthenticationWithAccessCodeGrant(false)
              .build();
  }
}
