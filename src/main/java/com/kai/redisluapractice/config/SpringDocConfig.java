package com.kai.redisluapractice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Practice Redis Lua script",
                version = "1.0",
                description = "版本: \n\n " +
                        "Java : 17\n\n" +
                        "spring boot : 3.2.2\n\n" +
                        "springdoc-openapi-core : 2.0.2\n\n")
)
@Configuration
public class SpringDocConfig {
}
