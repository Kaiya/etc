package com.icbc.provider.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Activate
public class IPWhiteListFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Properties prop = new Properties();
        InputStream in = this.getClass().getResourceAsStream("/ipwhitelist.properties");
        String clientIp = RpcContext.getContext().getRemoteHost();//获取客户端ip
        try {
            prop.load(in);
            String ipwhitelist = prop.getProperty("ipwhitelist");//ip白名单
            Boolean enable = new Boolean(prop.getProperty("enable"));
            if (!enable) {
                System.out.println("dubbo IP白名单被禁用！");
                return invoker.invoke(invocation);
            }
            if (ipwhitelist.contains(clientIp)) {
                System.out.println("dubbo客户端IP[" + clientIp + "]在白名单内");
                return invoker.invoke(invocation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RpcException e) {
            throw e;
        } catch (Throwable t) {
            throw new RpcException(t.getMessage(), t);
        }
        System.out.println("dubbo客户端IP[" + clientIp + "]不在白名单，禁止调用！");
        return new RpcResult();
    }
}
