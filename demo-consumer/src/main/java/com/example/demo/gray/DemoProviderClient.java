package com.example.demo.gray;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("demo-provider")
public interface DemoProviderClient {

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    String hello(@RequestParam("username") String username);
}
