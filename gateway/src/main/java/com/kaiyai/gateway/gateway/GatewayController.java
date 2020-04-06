package com.kaiyai.gateway.gateway;

import com.icbc.provider.service.CommonService;
import com.icbc.provider.service.HelloService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//import com.icbc.provider.service.HelloService;

/**
 * @author Kaiya Xiong
 * @date 2020-04-04
 */
@RestController
@RequestMapping("/api/cocoa")
public class GatewayController {

    @RequestMapping(value = "/test")
    public Map<String, Object> test(){
        Map<String, Object> resp;
        Map<String, Object> input = new HashMap<>();
        input.put("key", "kaiya");
        ReferenceConfig<HelloService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://192.168.123.108:20880/com.icbc.provider.impl.HelloServiceImpl");
        reference.setInterface(HelloService.class);
        HelloService hs = reference.get();
        resp = hs.execute(input);
        return resp;
    }

    @RequestMapping(value = "/json/{serviceId}/{methodName}", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> mainRoute(@PathVariable String serviceId, @PathVariable String methodName) {
        Map<String, Object> resp = new HashMap<>();
        Map<String, Object> input = new HashMap<>();
        input.put("key", "kaiya");
        Map<String, Object> output;
        ReferenceConfig<CommonService> reference = new ReferenceConfig<>();
        reference.setInterface(serviceId);
        reference.setGeneric("true");
        CommonService cs;
        try {
            Object o = reference.get();
//            why? true and false
            System.out.println("generic Service ? :" + (o instanceof GenericService));
            System.out.println("Common Service ? :" + (o instanceof CommonService));
            System.out.println(o.getClass().getName());
            if (o != null && o instanceof CommonService) {
                System.out.println("common");
                cs = (CommonService)o;
                output = cs.execute(input);
            } else if (o instanceof GenericService) {
                System.out.println("generic");
                output = (Map)((GenericService)o).$invoke(methodName, new String[]{Map.class.getName()}, new Object[]{input});

            } else {
                resp.put("result", "500200");
                return resp;
            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
            resp.put("result", "500200");
            return resp;
        } catch (Exception e){
            e.printStackTrace();
            resp.put("result", e.getMessage());
            return resp;
        }

        resp.put("service_id", serviceId);
        resp.put("result", output.get("result"));
        return resp;
    }
}
