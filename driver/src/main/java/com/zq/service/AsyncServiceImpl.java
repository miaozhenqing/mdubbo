package com.zq.service;

import com.zq.inter.AsyncService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/framework/async/
 * 异步调用
 */
@DubboService
public class AsyncServiceImpl implements AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
    final ExecutorService executorService = Executors.newFixedThreadPool(2, new ThreadFactory() {
        private final AtomicInteger threadNumber = new AtomicInteger(1);


        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "fixed-thread-" + threadNumber.getAndIncrement());
            thread.setDaemon(false); // 设置为用户线程
            thread.setPriority(Thread.NORM_PRIORITY); // 设置优先级为正常
            return thread;
        }
    });

    @Override
    public String invoke(String param) {
        logger.info("AsyncService invoke thread:{}", Thread.currentThread().getName());
        try {
            long time = ThreadLocalRandom.current().nextLong(1000);
            Thread.sleep(time);
            StringBuilder s = new StringBuilder();
            s.append("AsyncService invoke param:").append(param).append(",sleep:").append(time);
            return s.toString();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    @Override
    public CompletableFuture<String> asyncInvoke(String param) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                logger.info("AsyncService asyncInvoke thread:{}", Thread.currentThread().getName());
                // Do something
                long time = ThreadLocalRandom.current().nextLong(1000);
                Thread.sleep(time);
                StringBuilder s = new StringBuilder();
                s.append("AsyncService asyncInvoke param:").append(param).append(",sleep:").append(time);
                return s.toString();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return null;
        }, executorService);
    }
    @Override
    public String asyncContext(String name) {
        final AsyncContext asyncContext = RpcContext.startAsync();
        new Thread(() -> {
            logger.info("AsyncService asyncContext thread:{}", Thread.currentThread().getName());
            // 如果要使用上下文，则必须要放在第一句执行
            asyncContext.signalContextSwitch();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 写回响应
            asyncContext.write("Hello " + name + ", response from provider.");
        }).start();
        return null;
    }
}
