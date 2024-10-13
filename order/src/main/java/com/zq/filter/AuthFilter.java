package com.zq.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Activate(group = "dev", order = 2)
public class AuthFilter implements Filter  {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 请求前处理
        System.out.println("AuthFilter Before request");

        // 调用下一个过滤器
        Result result = invoker.invoke(invocation);

        // 响应后处理
        System.out.println("AuthFilter After response");

        return result;
    }
}
