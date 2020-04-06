package com.example.ccps;

import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.icbc.provider.service.CommonService;
import com.icbc.provider.service.HelloService;
import javassist.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CcpsApplicationTests {

//    @Reference
//    HelloService helloService;

    @Test
    public void contextLoads() {
        String interfaceName = "com.icbc.provider.service.HelloService";
        String methodName = "sayHello";
//        Map<String, Object> sayHello(Map<String, Object> input);
        // dynamically create interface that extends CommonService
        ClassPool defaultClassPool = ClassPool.getDefault();
        Class clazz = null;
        try {
            CtClass superInterface = defaultClassPool.getCtClass(CommonService.class
                    .getName());
            CtClass demoServiceInterface = defaultClassPool.makeInterface(interfaceName, superInterface);

            CtMethod method = CtNewMethod.abstractMethod(CtClass.voidType, methodName, new CtClass[]{}, null, demoServiceInterface);
            demoServiceInterface.addMethod(method);
            demoServiceInterface.writeFile("/Users/Kaiya/code/etc/ccps/target/classes/");
            clazz = demoServiceInterface.toClass();
        } catch (CannotCompileException e){
            e.printStackTrace();
        } catch (NotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        List<MethodConfig> methodConfigList = new ArrayList<>();
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName(methodName);
        methodConfigList.add(methodConfig);
        ReferenceConfig<CommonService> reference = new ReferenceConfig<>();
        reference.setInterface(clazz.getCanonicalName());
        reference.setMethods(methodConfigList);
//        reference.setInterface(HelloService.class);
        CommonService helloService = reference.get();
        Map<String, Object> input = new HashMap<>();
        input.put("key", "kaiya");
        Map<String, Object> output = helloService.execute(input);
        System.out.println(output.get("result"));
    }

}
