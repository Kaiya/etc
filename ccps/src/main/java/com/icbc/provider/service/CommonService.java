package com.icbc.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Kaiya Xiong
 * @date 2020-04-04
 */
@Service
@Component
public interface CommonService {

    Map<String, Object> execute(Map<String, Object> input);
}
