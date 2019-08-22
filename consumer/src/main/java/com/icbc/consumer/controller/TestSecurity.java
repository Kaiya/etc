package com.icbc.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kaiya Xiong
 * @date 2019-08-22
 */
@RestController
public class TestSecurity {
    public class TestController {

        @GetMapping("getData")
        public String getData() {
            return "date";
        }

    }
}
