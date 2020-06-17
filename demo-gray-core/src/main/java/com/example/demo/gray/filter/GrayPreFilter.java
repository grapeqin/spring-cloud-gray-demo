package com.example.demo.gray.filter;

import com.example.demo.gray.util.HystrixRequestVariableDefaultUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

public class GrayPreFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(GrayPreFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
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
        RequestContext ctx = RequestContext.getCurrentContext();
        //TODO 根据实际情况解析灰度策略
        String version = ctx.getRequest().getHeader(HystrixRequestVariableDefaultUtils.HEADER_VERSION);

        if (StringUtils.isNotBlank(version)) {
            log.info("uri = {},version = {} start gray", ctx.getRequest().getRequestURI(), version);
        }
        HystrixRequestVariableDefaultUtils.initHystrixRequestContext(version);
        ctx.addZuulRequestHeader(HystrixRequestVariableDefaultUtils.HEADER_VERSION, version);
        return null;
    }
}
