package com.example.demo.mihnea.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;

//package com.example.demo.mihnea.swagger;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
//
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("Test")
                        .description("Spring")
                        .version("1.0"));
    }
}
