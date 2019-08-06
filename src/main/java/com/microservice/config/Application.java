package com.microservice.config;

import com.microservice.config.archaius.ConsulPropertySourceInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication(Application.class);
        sa.addInitializers(new ConsulPropertySourceInitializer());
        sa.run(args);
    }
}
