package com.zq.config;

import com.alibaba.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
public class ServerConfig {
    @Value("${spring.application.name}")
    public String serverName;
    @Value("${server.port}")
    public String port;
}
