package com.qzq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Web2App  {
    public static void main(String[] args) {
        SpringApplication.run(Web2App.class, args);
    }
}
