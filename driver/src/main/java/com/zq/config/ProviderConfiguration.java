package com.zq.config;

import com.zq.inter.DemoService;
import com.zq.service.DemoServiceImpl;
import com.zq.service.DemoServiceV3Impl;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.spring.ServiceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProviderConfiguration {
    @Bean
    public ServiceConfig demoService() {
        ServiceConfig service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceV3Impl());
        service.setGroup("dev");
        service.setVersion("3");
        Map<String, String> parameters = new HashMap<>();
        service.setParameters(parameters);
        return service;
    }
}
