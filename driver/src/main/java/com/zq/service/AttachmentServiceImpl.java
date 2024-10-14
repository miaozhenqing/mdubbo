package com.zq.service;

import com.zq.inter.AttachmentService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/tasks/framework/attachment/
 * 传递附加参数
 */
@DubboService
public class AttachmentServiceImpl implements AttachmentService {
    @Override
    public String attachment(String name) {
        // 获取客户端隐式传入的参数，比如用于框架集成
        String index = RpcContext.getServerAttachment().getAttachment("index");
        RpcContext.getServerContext().setAttachment("result", 2);
        return "hello " + name + ", response from provider: " + index;
    }
}
