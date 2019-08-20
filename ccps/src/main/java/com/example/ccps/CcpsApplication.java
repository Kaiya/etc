package com.example.ccps;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@MapperScan(value = "com.example.ccps.maper")
@EnableDubbo
@SpringBootApplication

public class CcpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcpsApplication.class, args);
    }

}
