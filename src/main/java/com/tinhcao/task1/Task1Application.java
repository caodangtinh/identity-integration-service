package com.tinhcao.task1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tinhcao"})
public class Task1Application {
    public static void main(String[] args) {
        SpringApplication.run(Task1Application.class, args);
        System.out.println("Welcome to backbase, again");
    }
}
