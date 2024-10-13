package com.zq.controller;

import com.zq.inter.DemoService;
import com.zq.proto.DubboGreeterTriple;
import com.zq.proto.Greeter;
import com.zq.proto.GreeterReply;
import com.zq.proto.GreeterRequest;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

import static org.apache.dubbo.registry.multiple.MultipleRegistry.LOGGER;

@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/service-discovery/loadbalance/
     * 接口级配置负载均衡策略
     */
    @DubboReference(version = "1", group = "dev", timeout = 5000, loadbalance = "roundrobin")
    private DemoService demoService;
    @DubboReference(version = "1", group = "dev", timeout = 5000)
    private Greeter greeter;

    @RequestMapping("/hello")// http://localhost:8006/order/hello?version=testV&name=zhangsan&sleep=0
    public String hello(@RequestParam("version") String version, @RequestParam("name") String name, @RequestParam("sleep") int sleep) {
        String res = demoService.sayHello(version, name, sleep);
        return res;
    }

    @RequestMapping("/greet")// http://localhost:8006/order/greet?name=zhangsan
    public String greet(@RequestParam("name") String name) {
        GreeterReply greeterReply = greeter.greet(GreeterRequest.newBuilder().setName(name).build());
        return greeterReply.getMessage();
    }

    @RequestMapping("/greetAsync")// http://localhost:8006/order/greetAsync?name=toooom
    public String greetAsync(@RequestParam("name") String name) {
        CompletableFuture<GreeterReply> future = greeter.greetAsync(GreeterRequest.newBuilder().setName(name).build());
        future.whenComplete((reply, ex) -> LOGGER.info("greetAsync: {}", reply.getMessage()));
        return "ok";
    }

    /**
     * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/protocols/triple/streaming/
     */
    @RequestMapping("/greetBiStream")// http://localhost:8006/order/greetBiStream?name=toooom
    public String greetBiStream(@RequestParam("name") String name) {
        StreamObserver<GreeterRequest> requestStreamObserver = greeter.biStream(new SampleStreamObserver());
        for (int i = 0; i < 10; i++) {
            GreeterRequest request = GreeterRequest.newBuilder().setName("name-" + i).build();
            requestStreamObserver.onNext(request);
        }
        requestStreamObserver.onCompleted();
        return "ok";
    }
    @RequestMapping("/greetServerStream")// http://localhost:8006/order/greetServerStream?name=toooom
    public String greetServerStream(@RequestParam("name") String name) {
        GreeterRequest request = GreeterRequest.newBuilder().setName("server stream request.").build();
        greeter.serverStream(request, new SampleStreamObserver());
        return "ok";
    }

    private static class SampleStreamObserver implements StreamObserver<GreeterReply>, Serializable {

        @Override
        public void onNext(GreeterReply data) {
            LOGGER.info("stream <- reply:{}", data);
        }

        @Override
        public void onError(Throwable throwable) {
            LOGGER.error("stream onError", throwable);
            throwable.printStackTrace();
        }

        @Override
        public void onCompleted() {
            LOGGER.info("stream completed");
        }
    }
}
