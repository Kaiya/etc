package com.icbc.provider.service;

import java.util.Map;

/**
 * @author Kaiya Xiong
 * @date 2020-04-05
 */
public interface HelloService extends CommonService {
    Map<String, Object> sayHello(Map<String, Object> input);
}
