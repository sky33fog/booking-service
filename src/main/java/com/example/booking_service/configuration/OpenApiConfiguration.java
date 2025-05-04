package com.example.booking_service.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI openApiDescription() {
        Server localhost = new Server();
        localhost.setUrl("http://localhost:8080");
        localhost.setDescription("Local env");

        Contact contact = new Contact();
        contact.setName("Vadim Saraev");
        contact.setEmail("sky33fog@gmail.com");

        Info info = new Info()
                .title("Booking service API")
                .version("1.0")
                .contact(contact)
                .description("API for booking service");

        return new OpenAPI().info(info).servers(List.of(localhost));
    }
}
