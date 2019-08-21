package com.icbc.provider.service;

/**
 * @author Kaiya Xiong
 * @date 2019-08-21
 */
public interface BatchService {
    /**
     * 改变批量任务黑名单导出到CSV文件的频度
     * @param cron cron 表达式
     * @return 是否更改成功
     */
    Boolean changeBlacklistCsvCron(String cron);

    /**
     * 改变批量任务登记簿到黑名单的频度
     * @param cron cron 表达式
     * @return 是否更改成功
     */
    Boolean changeRegisterBlacklistCron(String cron);
}
