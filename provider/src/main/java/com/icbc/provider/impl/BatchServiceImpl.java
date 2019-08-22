package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.batch.BatchBlacklistCsv;
import com.icbc.provider.batch.BatchRegisterBlacklist;
import com.icbc.provider.mapper.BlacklistMapper;
import com.icbc.provider.mapper.RegisterMapper;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.BatchService;
import com.icbc.provider.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Autowired
    BlacklistMapper blacklistMapper;
    @Autowired
    RegisterMapper registerMapper;

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

    @Override
    public Boolean incrementalUpdateBlackList() {
        List<Register> registerList = registerMapper.registerLeftJoinBlackList();
        if (registerList.isEmpty()) {
            System.out.println("登记簿没有新增数据");
            return false;
        } else {
            if (blacklistMapper.insertIncrementalBlackList(registerList) > 0) {
                return true;
            } else {
                return false;
            }
        }
    }


}
