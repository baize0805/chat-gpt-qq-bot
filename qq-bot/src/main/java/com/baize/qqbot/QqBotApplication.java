package com.baize.qqbot;

import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSimbot
@SpringBootApplication
public class QqBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QqBotApplication.class, args);
    }

}
