package com.example.demo.gray;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class DemoProviderApplication {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(DemoProviderApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam("username") String username) {
        StringBuilder builder = new StringBuilder("hello ")
                .append(username)
                .append(". this provider is ")
                .append(applicationName)
                .append(":")
                .append(port);
        return builder.toString();
    }
}
