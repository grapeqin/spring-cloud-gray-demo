package com.example.demo.gray.rule;

import com.netflix.loadbalancer.*;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义RibbonRulePropertiesFactory
 */
public class CustomRibbonPropertiesFactory extends PropertiesFactory {

    private Map<Class, String> classToProperty = new HashMap<>();

    /**
     * 保存灰度实现类
     */
    private Map<String, String> propertyToClassName = new HashMap<>();

    public CustomRibbonPropertiesFactory() {
        super();
        classToProperty.put(ILoadBalancer.class, "NFLoadBalancerClassName");
        classToProperty.put(IPing.class, "NFLoadBalancerPingClassName");
        classToProperty.put(IRule.class, "NFLoadBalancerRuleClassName");
        classToProperty.put(ServerList.class, "NIWSServerListClassName");
        classToProperty.put(ServerListFilter.class, "NIWSServerListFilterClassName");

        propertyToClassName.put("NFLoadBalancerRuleClassName", GrayMetadataRule.class.getCanonicalName());
    }

    @Override
    public String getClassName(Class clazz, String name) {
        //先取每个service配置的规则类
        String className = super.getClassName(clazz, name);

        // 读取灰度配置
        if (!StringUtils.hasText(className) && this.classToProperty.containsKey(clazz)) {
            String classNameProperty = this.classToProperty.get(clazz);
            className = propertyToClassName.get(classNameProperty);
        }
        return className;
    }
}
