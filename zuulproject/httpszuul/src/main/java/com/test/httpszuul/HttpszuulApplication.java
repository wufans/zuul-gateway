package com.test.httpszuul;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@EnableZuulProxy
@SpringBootApplication
public class HttpszuulApplication {


    public static void main(String[] args) {
        SpringApplication.run(HttpszuulApplication.class, args);
    }



}
