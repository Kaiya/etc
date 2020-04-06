package com.kaiyai.gateway;

import com.icbc.provider.service.CommonService;
import com.icbc.provider.service.HelloService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Kaiya Xiong
 * @date 2020-04-05
 */
@SpringBootTest
@RunWith(SpringRunner.class)
//@EnableDubbo
public class CosumerTest {

    @Reference
    HelloService helloService;
    @Test
    public void testDynamicLoadInterface(){
//        ClassPool defaultClassPool = ClassPool.getDefault();
//        CtClass superInterface = defaultClassPool.getCtClass(CommonService.class
//                .getName());
//        CtClass demoServiceInterface = defaultClassPool.makeInterface("DemoService", superInterface);
////        demoServiceInterface.writeFile("/Users/Kaiya/Desktop");
//        Class clazz = demoServiceInterface.toClass();
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
//        context.start();
        Map<String, Object> input = new HashMap<>();
        input.put("key", "kaiya");
//        HelloService helloService = (HelloService)context.getBean("helloService");

        Map<String, Object> output = helloService.execute(input);
        System.out.println(output.get("result"));
    }

//    @Reference
//    HelloService hService;
//    @Test
    public void invoke() throws IOException, ExecutionException, InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        Map<String, Object> input = new HashMap<>();
        input.put("key", "kaiya");
        ReferenceConfig<CommonService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20880/com.icbc.provider.impl.HelloServiceImpl");
        reference.setInterface(CommonService.class);
        CommonService hs = reference.get();

//        CommonService service = context.getBean("helloService", CommonService.class);
        Map<String, Object> output = hs.execute(input);
        Thread.sleep(900000);
        Future<Map> f = RpcContext.getContext().getFuture();
        if (f.isDone()){
            System.out.println(f.get().get("result"));
        }
        System.out.println(output);

        System.in.read();
    }
}
