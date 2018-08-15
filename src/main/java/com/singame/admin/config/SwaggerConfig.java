package com.singame.admin.config;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
  @Bean
  public Docket api() {
    // 添加head参数start
    ParameterBuilder tokenPar = new ParameterBuilder();
    List<Parameter> pars = new ArrayList<Parameter>();
    tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
            .required(true).build();
    pars.add((Parameter) tokenPar.build());
        // 添加head参数end

    return new Docket(DocumentationType.SWAGGER_2)
                    .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                    .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.singame.admin.controller"))
                    .paths(PathSelectors.any())
                    .build()
                    .globalOperationParameters(pars)
                    .apiInfo(apiInfo());
  }
  
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
                .title("国泰君安股权投资API")
                .description("国泰君安股权系统API")
                .termsOfServiceUrl("")
                .version("1.0")
                .contact(new Contact("李宝剑", "", "lixu@chengantech.com"))
                .build();
  }
}
