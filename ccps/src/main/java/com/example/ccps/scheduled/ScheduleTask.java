package com.example.ccps.scheduled;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {

    @Autowired
    SyncBlacklist syncBlacklist;
    private Log log = LogFactory.getLog(ScheduleTask.class);


    @Scheduled(cron = "0/50 * * * * ?")
    private void configureTasks() {
        if (syncBlacklist.syncBlacklist()) {
            log.info("CCPS:黑名单同步完成");
        }else{
            log.error("CCPS:黑名单同步出现异常");
        }

    }

}