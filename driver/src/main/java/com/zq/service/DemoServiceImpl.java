package com.zq.service;

import com.zq.config.ServerConfig;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.dubbo.registry.multiple.MultipleRegistry.LOGGER;

@DubboService(version = "1", group = "dev", timeout = 5000)
public class DemoServiceImpl implements com.zq.inter.DemoService {

    @Autowired
    ServerConfig serverConfig;
    @Override
    public String sayHello(String version, String name, int sleep) {
        LOGGER.info("sayHello receive request, name: {}", name);
        if (sleep > 0) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        StringBuilder builder = new StringBuilder();
        StringBuilder append = builder.append("version:").append(version).append("</br>")
                .append("hello:").append(name).append("</br>")
                .append("class:").append(this.getClass().getSimpleName()).append("</br>")
                .append("port:").append(serverConfig.port).append("</br>");
        return append.toString();
    }

}