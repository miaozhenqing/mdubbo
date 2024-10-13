package com.zq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    @Value("${spring.application.name}")
    public String serverName;
    @Value("${server.port}")
    public String port;
}
