package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.icbc.provider.service.CommonService;
import com.icbc.provider.service.HelloService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kaiya Xiong
 * @date 2020-04-04
 */
@Component
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public Map<String, Object> execute(Map<String, Object> input) {
        Map<String, Object> output = new HashMap<>();
        output.put("result", "Hello, " + input.get("key"));
        System.out.println("url: "+RpcContext.getContext().getUrl());
        return output;
    }

    @Override
    public Map<String, Object> sayHello(Map<String, Object> input) {
        Map<String, Object> output = new HashMap<>();
        output.put("result", "Say Hello, " + input.get("key"));
        return output;
    }
}
