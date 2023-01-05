package com.norato.easymall.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    private static final String SECURITY_SCHEMA_NAME = "bearerAuth";

    @Bean
    public OpenAPI easymallOpenAPI() {
        Info info = new Info().title("EasyMall API").version("1.0").description("EasyMall 接口文档");
        SecurityScheme securityScheme = new SecurityScheme()
                                            .name(SECURITY_SCHEMA_NAME)
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT");
        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEMA_NAME))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEMA_NAME, securityScheme));
    }

    @Bean
    public GroupedOpenApi userOpenApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/**")
                .pathsToExclude("/admin/**")
                .packagesToScan("com.norato.easymall.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi adminOpenApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/admin/**")
                .packagesToScan("com.norato.easymall.controller")
                .build();
    }
}
