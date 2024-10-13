package com.zq.service;

import com.zq.config.ServerConfig;
import com.zq.proto.DubboGreeterTriple;
import com.zq.proto.GreeterReply;
import com.zq.proto.GreeterRequest;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static org.apache.dubbo.registry.multiple.MultipleRegistry.LOGGER;

/**
 * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/protocols/triple/idl/
 */
@DubboService(version = "1", group = "dev", timeout = 5000)
@Service
public class GreeterImpl extends DubboGreeterTriple.GreeterImplBase {
    @Autowired
    ServerConfig serverConfig;

    @Override
    public GreeterReply greet(GreeterRequest request) {
        LOGGER.info("Server {} received greet request {}", serverConfig.serverName, request);
        return GreeterReply.newBuilder()
                .setMessage("hello," + request.getName())
                .build();
    }

    /**
     * Streaming 通信
     * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/protocols/triple/streaming/
     */
    @Override
    public StreamObserver<GreeterRequest> biStream(StreamObserver<GreeterReply> responseObserver) {
        return new StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                GreeterReply resp = GreeterReply.newBuilder().setMessage("reply from biStream " + data.getName()).build();
                responseObserver.onNext(resp);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };
    }

    @Override
    public void serverStream(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        LOGGER.info("receive request: {}", request.getName());
        for (int i = 0; i < 10; i++) {
            GreeterReply reply = GreeterReply.newBuilder().setMessage("reply from serverStream. " + i).build();
            responseObserver.onNext(reply);
        }
        responseObserver.onCompleted();
    }
}
