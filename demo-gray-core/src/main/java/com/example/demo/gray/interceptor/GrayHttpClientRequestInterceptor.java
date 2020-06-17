package com.example.demo.gray.interceptor;

import com.example.demo.gray.util.HystrixRequestVariableDefaultUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

/**
 * HttpClient 请求的拦截器
 */
public class GrayHttpClientRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        String hystrixVer = HystrixRequestVariableDefaultUtils.version.get();
        requestWrapper.getHeaders().add(HystrixRequestVariableDefaultUtils.HEADER_VERSION, hystrixVer);
        return execution.execute(requestWrapper, body);
    }
}
