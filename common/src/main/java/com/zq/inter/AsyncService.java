package com.zq.inter;

import java.util.concurrent.CompletableFuture;

/**
 * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/framework/async/
 * 异步调用
 */
public interface AsyncService {
    /**
     * 同步调用方法
     */
    String invoke(String param);
    /**
     * 异步调用方法
     */
    CompletableFuture<String> asyncInvoke(String param);

    /**
     * 使用AsyncContext
     */
    String asyncContext(String name);
}
