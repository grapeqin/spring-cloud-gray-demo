package com.example.demo.gray.interceptor;

import com.example.demo.gray.util.HystrixRequestVariableDefaultUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 把请求头中需要进行灰度相关的信息保存到线程变量中，以便于Ribbon处理
 */
public class GrayHeaderVersionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HystrixRequestVariableDefaultUtils.initHystrixRequestContext(request.getHeader(HystrixRequestVariableDefaultUtils.HEADER_VERSION));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HystrixRequestVariableDefaultUtils.shutdownHystrixRequestContext();
    }
}
