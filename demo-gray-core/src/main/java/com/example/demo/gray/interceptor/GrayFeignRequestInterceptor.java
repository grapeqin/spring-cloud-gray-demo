package com.example.demo.gray.interceptor;

import com.example.demo.gray.util.HystrixRequestVariableDefaultUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign请求的拦截器
 */
public class GrayFeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String hystrixVer = HystrixRequestVariableDefaultUtils.version.get();
        template.header(HystrixRequestVariableDefaultUtils.HEADER_VERSION, hystrixVer);
    }
}
