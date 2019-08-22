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

    /**
     *
     * @return -1 没有新增数据，0 更新失败，1 更新成功
     */
    @Override
    public int incrementalUpdateBlackList() {
        List<Register> registerList = registerMapper.registerLeftJoinBlackList();
        if (registerList.isEmpty()) {
            return -1;
        } else {
            if (blacklistMapper.insertIncrementalBlackList(registerList) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }


}
