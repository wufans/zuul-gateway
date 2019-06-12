package com.test.httpseureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class HttpseurekaApplication {


    public static void main(String[] args) {
        SpringApplication.run(HttpseurekaApplication.class, args);
    }

}
