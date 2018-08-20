package com.singame.admin.config;

import java.util.ArrayList;
import java.util.List;

import com.singame.admin.dto.UserAuthDTO;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
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
    // 添加head参数start
    ParameterBuilder tokenPar = new ParameterBuilder();
    tokenPar.name("Authorization")
            .description("令牌")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .required(true)
            .build();

    List<Parameter> pars = new ArrayList<Parameter>();
    pars.add((Parameter) tokenPar.build());
    // 添加head参数end
    return new Docket(DocumentationType.SWAGGER_2)
                    .ignoredParameterTypes(UserAuthDTO.class)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.singame.admin.controller"))
                    .paths(PathSelectors.any())
                    .build()
                    .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                    .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                    .globalOperationParameters(pars)
                    .apiInfo(apiInfo())
                    .globalResponseMessage(RequestMethod.GET, defaultResp)
                    .globalResponseMessage(RequestMethod.POST, defaultResp)
                    .globalResponseMessage(RequestMethod.PUT, defaultResp)
                    .globalResponseMessage(RequestMethod.DELETE, defaultResp);
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
}
