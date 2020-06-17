package com.example.demo.gray.config;

import com.example.demo.gray.filter.GrayPostFilter;
import com.example.demo.gray.interceptor.GrayFeignRequestInterceptor;
import com.example.demo.gray.interceptor.GrayHttpClientRequestInterceptor;
import com.example.demo.gray.filter.GrayPreFilter;
import com.example.demo.gray.interceptor.GrayHeaderVersionInterceptor;
import com.example.demo.gray.rule.CustomRibbonPropertiesFactory;
import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class GrayConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public GrayPreFilter grayFilter() {
        return new GrayPreFilter();
    }

    @Bean
    public GrayPostFilter grayPostFilter() {
        return new GrayPostFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 对http请求进行灰度标识
        registry.addInterceptor(new GrayHeaderVersionInterceptor());
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new GrayHttpClientRequestInterceptor());
        return restTemplate;
    }

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder().requestInterceptor(new GrayFeignRequestInterceptor());
    }

    @Bean
    public CustomRibbonPropertiesFactory defaultPropertiesFactory() {
        return new CustomRibbonPropertiesFactory();
    }

}
