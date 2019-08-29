package com.icbc.provider.batch;

import com.icbc.provider.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kaiya Xiong
 * @date 2019-08-18
 */
@Service
public class BatchBlacklistCsv implements SchedulingConfigurer {

    private static final String DEFAULT_CRON = "0/50 * * * * ?";
    //    每天凌晨3点执行批量任务
//    private static final String DEFAULT_CRON = "0 0 3 1/1 * ?";
    private String cron = DEFAULT_CRON;
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    BlacklistService blacklistService;

    // TODO: 2019-08-20 增量更新还是全量更新？
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            // 定时任务的业务逻辑
            System.out.println("------------------开始执行批量任务------------------");
//
//            String pathPrefix = this.getClass().getResource("/csv/").getPath();
//            String csvPath = pathPrefix + "ccia_blacklist_" + df.format(new Date()) + ".csv";
            String ccia_csvPath = "./provider/target/classes/csv/ccia_blacklist_" + df.format(new Date()) + ".csv";
//            System.out.println("csv path: " + csvPath);
            if (blacklistService.batchExportToFile(ccia_csvPath)) {
                System.out.println("黑名单导出到csv成功");
            }
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
