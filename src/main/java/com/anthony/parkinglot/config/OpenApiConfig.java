package com.anthony.parkinglot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Parking Lot Web Service APIs")
                        .version("1.0.0")
                        .description("This is the api docs for all the endpoints of the Parking Lot"));
    }
}