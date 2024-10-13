package com.zq.filter;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
@Activate(group = "dev", order = 1)
public class LogFilter implements Filter  {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 请求前处理
        System.out.println("LogFilter Before request");

        // 调用下一个过滤器
        Result result = invoker.invoke(invocation);

        // 响应后处理
        System.out.println("LogFilter After response");

        return result;
    }
}
