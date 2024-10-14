package com.zq.inter;

import java.util.concurrent.CompletableFuture;

/**
 * 泛化调用
 * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/framework/generic/
 *
 * https://www.cnblogs.com/thisiswhy/p/17859011.html
 */
public interface GenericInvokeService {
    String sayHello(String name);
    CompletableFuture<String> sayHelloAsync(String name);
}
