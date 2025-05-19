package com.example.taskmanager.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager API")
                        .description("API documentation for Task Management System")
                        .version("1.0"));
    }
}
