package com.example.qbots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class QbotsApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(QbotsApplication.class, args);
    }

}