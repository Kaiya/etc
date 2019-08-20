package com.example.ccps.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {
    private static ArrayList<String[]> fileStateList = UpdateBlacklist.getFileList();
    @Autowired
    UpdateBlacklist updateBlacklist;
    @Scheduled(fixedRate=1000)
    private void configureTasks() {
        fileStateList = updateBlacklist.getFileState(fileStateList);
    }

}