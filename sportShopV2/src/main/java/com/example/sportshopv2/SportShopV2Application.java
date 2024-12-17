package com.example.sportshopv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SportShopV2Application {

    public static void main(String[] args) {
        SpringApplication.run(SportShopV2Application.class, args);
    }

}
