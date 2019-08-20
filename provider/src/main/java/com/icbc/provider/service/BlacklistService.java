package com.icbc.provider.service;

import com.icbc.provider.model.Blacklist;

/**
 * @author Kaiya Xiong
 * @date 2019-08-15
 */
public interface BlacklistService {


    /**
     * 批量将登记簿的记录读出来，加入到黑名单中（批量任务，每天凌晨执行）
     *
     * @return 是否添加成功
     */
    Boolean batchAddBlacklist();

}
