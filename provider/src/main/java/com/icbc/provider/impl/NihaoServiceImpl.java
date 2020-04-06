package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.service.NihaoService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kaiya Xiong
 * @date 2020-04-05
 */
@Component
@Service
public class NihaoServiceImpl implements NihaoService {
    @Override
    public Map<String, Object> execute(Map<String, Object> input) {
        Map<String, Object> output = new HashMap<>();
        output.put("result", "Nihao, " + input.get("key"));
        return output;
    }
}
