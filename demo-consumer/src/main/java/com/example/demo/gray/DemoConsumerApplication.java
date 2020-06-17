package com.example.demo.gray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class DemoConsumerApplication {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private int port;

    @Autowired
    private DemoProviderClient demoProviderClient;

    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam("username") String username) {
        StringBuilder builder = new StringBuilder("client is ")
                .append(applicationName)
                .append(":")
                .append(port)
                .append("  ")
                .append(demoProviderClient.hello(username));
        return builder.toString();
    }
}
