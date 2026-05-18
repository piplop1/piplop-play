package com.piplop.play.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PiplopPlay API")
                        .version("1.0.0")
                        .description("API REST para gestión de películas en la plataforma PiplopPlay")
                        .contact(new Contact()
                                .name("Leonardo")
                                .email("leonardo.cuevadiaz@gmail.com")));
    }
}
