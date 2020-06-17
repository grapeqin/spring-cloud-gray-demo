package com.example.demo.gray.util;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import org.springframework.util.StringUtils;

public class HystrixRequestVariableDefaultUtils {

    public static final String HEADER_VERSION = "version";

    /**
     * 运用HystrixRequestVariableDefault来保证跨线程的变量传递
     */
    public static final HystrixRequestVariableDefault<String> version = new HystrixRequestVariableDefault<>();

    public static void initHystrixRequestContext(String headerVer) {
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
        }

        if (!StringUtils.isEmpty(headerVer)) {
            version.set(headerVer);
        }
    }

    public static void shutdownHystrixRequestContext() {
        if (HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.getContextForCurrentThread().shutdown();
        }
    }
}
