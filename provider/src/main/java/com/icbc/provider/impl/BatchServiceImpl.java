package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.batch.BatchBlacklistCsv;
import com.icbc.provider.batch.BatchRegisterBlacklist;
import com.icbc.provider.service.BatchService;
import com.icbc.provider.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Kaiya Xiong
 * @date 2019-08-21
 */
@Service
@Component
public class BatchServiceImpl implements BatchService {

    @Autowired
    BatchBlacklistCsv batchBlacklistCsv;
    @Autowired
    BatchRegisterBlacklist batchRegisterBlacklist;
    @Autowired
    BlacklistService blacklistService;

    @Override
    public Boolean batchBlacklistCsv(String path) {
        return blacklistService.batchExportToFile(path);
    }

    @Override
    public Boolean batchRegisterBlacklist() {
        return blacklistService.batchAddBlacklist();
    }

    @Override
    public Boolean changeBlacklistCsvCron(String cron) {
        batchBlacklistCsv.setCron(cron);
//        batchBlacklistCsv.configureTasks();
        return null;
    }

    @Override
    public Boolean changeRegisterBlacklistCron(String cron) {
        batchRegisterBlacklist.setCron(cron);

        return null;
    }
}
