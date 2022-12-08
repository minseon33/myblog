package com.example.dailyblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DailyblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyblogApplication.class, args);
    }

}
