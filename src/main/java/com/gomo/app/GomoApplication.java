package com.gomo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GomoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GomoApplication.class, args);
    }

}
