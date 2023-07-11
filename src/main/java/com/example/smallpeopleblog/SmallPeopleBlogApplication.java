package com.example.smallpeopleblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SmallPeopleBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallPeopleBlogApplication.class, args);
    }

}