package com.qzq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WebDuplicateApp  {
    public static void main(String[] args) {
        SpringApplication.run(WebDuplicateApp.class, args);
    }
}