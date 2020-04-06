package com.example.ccps;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.icbc.provider.service.CommonService;
import com.icbc.provider.service.HelloService;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CcpsApplicationTests {

//    @Reference
//    HelloService helloService;

    @Test
    public void contextLoads() {


        // dynamically create interface that extends CommonService
        ClassPool defaultClassPool = ClassPool.getDefault();
        Class clazz = null;
        try {
            CtClass superInterface = defaultClassPool.getCtClass(CommonService.class
                    .getName());
            CtClass demoServiceInterface = defaultClassPool.makeInterface("com.icbc.provider.service.NihaoService", superInterface);
            clazz = demoServiceInterface.toClass();
            demoServiceInterface.writeFile("/Users/Kaiya/code/etc/ccps/target/classes/");
        } catch (CannotCompileException e){
            e.printStackTrace();
        } catch (NotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        ReferenceConfig<CommonService> reference = new ReferenceConfig<>();
        reference.setInterface(clazz.getCanonicalName());
//        reference.setInterface(HelloService.class);
        CommonService helloService = reference.get();
        Map<String, Object> input = new HashMap<>();
        input.put("key", "kaiya");
        Map<String, Object> output = helloService.execute(input);
        System.out.println(output.get("result"));
        assert output.get("result").equals("Nihao, kaiya");
    }

}
