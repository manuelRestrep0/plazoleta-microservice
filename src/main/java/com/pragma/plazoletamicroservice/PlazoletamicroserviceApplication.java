package com.pragma.plazoletamicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlazoletamicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlazoletamicroserviceApplication.class, args);
    }

}
