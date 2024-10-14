package com.zq.service;

import com.zq.inter.GenericInvokeService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.concurrent.CompletableFuture;
/**
 * 泛化调用
 * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/framework/generic/
 * 泛化实现
 * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/framework/more/generic-impl/
 *
 * https://www.cnblogs.com/thisiswhy/p/17859011.html
 */
@DubboService
public class GenericInvokeServiceImpl implements GenericInvokeService, GenericService{
    @Override
    public String sayHello(String name) {
        return "sayHello: " + name;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.supplyAsync(() -> {
            return "sayHelloAsync: " + name;
        });
    }

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        if (method.equals("sayHello")) {
            System.out.print("executing sayHello.");
            throw new RuntimeException("sayHello: throws exception");
        } else if (method.equals("sayHelloAsync")) {
            System.out.print("executing sayHelloAsync.");
            return CompletableFuture.completedFuture("sayHelloAsync: hello " + args[0]);
        } else {
            try {
                return defaultOperation(method, parameterTypes, args);
            } catch (Exception e) {
                throw new GenericException(e);
            }
        }
    }
    private Object defaultOperation(String method, String[] parameterTypes, Object[] args) throws Exception {
        throw new UnsupportedOperationException("method does not exist.");
    }
    @Override
    public CompletableFuture<Object> $invokeAsync(String method, String[] parameterTypes, Object[] args) throws GenericException {
        return GenericService.super.$invokeAsync(method, parameterTypes, args);
    }
}
