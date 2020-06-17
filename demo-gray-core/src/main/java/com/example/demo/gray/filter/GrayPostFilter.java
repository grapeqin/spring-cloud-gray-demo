package com.example.demo.gray.filter;

import com.example.demo.gray.util.HystrixRequestVariableDefaultUtils;
import com.netflix.zuul.ZuulFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

public class GrayPostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        HystrixRequestVariableDefaultUtils.shutdownHystrixRequestContext();
        return null;
    }
}
