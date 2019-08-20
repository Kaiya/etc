package com.icbc.provider.controller;

import com.icbc.provider.batch.BatchRegisterCsv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kaiya Xiong
 * @date 2019-08-18
 */
@RestController
@RequestMapping("/dynamic-scheduled")
public class DynamicSchedulerController {

    @Autowired
    BatchRegisterCsv batchRegisterBlacklist;

    @RequestMapping(value = "/update-cron")
    public String updateDynamicScheduledTask(@RequestParam("cron") String cron) {

        batchRegisterBlacklist.setCron(cron);

        return "success";
    }
}
