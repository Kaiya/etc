package com.icbc.provider.batch;

import com.icbc.provider.service.BatchService;
import com.icbc.provider.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Kaiya Xiong
 * @date 2019-08-19
 */
@Service
public class BatchRegisterBlacklist implements SchedulingConfigurer {

//    private static final String DEFAULT_CRON = "0/50 * * * * ?";
        private static final String DEFAULT_CRON = "0 0 3 1/1 * ?";
    private String cron = DEFAULT_CRON;

    @Autowired
    BlacklistService blacklistService;
    @Autowired
    BatchService batchService;

    // TODO: 2019-08-20 增量更新 BlacklistServiceImpl.java 里面 已实现，在BatchServiceImpl.java
    // TODO: 2019-08-20 两个批量异步执行 
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            // 定时任务的业务逻辑
            System.out.println("------------------开始执行批量任务------------------");
            int result = batchService.incrementalUpdateBlackList();
            if (result == -1) System.out.println("登记簿没有新增数据");
            else if (result == 1) System.out.println("登记簿增量导入到黑名单成功");
            else System.out.println("登记簿增量导入到黑名单失败");
        }, (triggerContext) -> {
            // 定时任务触发，可修改定时任务的执行周期
            CronTrigger trigger = new CronTrigger(cron);
            Date nextExecDate = trigger.nextExecutionTime(triggerContext);
            return nextExecDate;
        });

    }

    public void setCron(String cron) {
        System.out.println("当前cron=" + this.cron + "->将被改变为：" + cron);
        this.cron = cron;
    }

}
