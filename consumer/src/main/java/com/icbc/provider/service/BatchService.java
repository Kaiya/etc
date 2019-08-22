package com.icbc.provider.service;

/**
 * @author Kaiya Xiong
 * @date 2019-08-21
 */
public interface BatchService {
    /**
     * 手动执行批量将黑名单导出到CSV文件
     *
     * @param path CSV文件路径
     * @return 是否成功
     */
    Boolean batchBlacklistCsv(String path);


    /**
     * 手动执行批量将登记簿记录添加到黑名单
     *
     * @return 是否成功
     */
    Boolean batchRegisterBlacklist();

    /**
     * 改变批量任务黑名单导出到CSV文件的频度
     *
     * @param cron cron 表达式
     * @return 是否更改成功
     */
    Boolean changeBlacklistCsvCron(String cron);

    /**
     * 改变批量任务登记簿到黑名单的频度
     *
     * @param cron cron 表达式
     * @return 是否更改成功
     */
    Boolean changeRegisterBlacklistCron(String cron);

    /**
     * 将登记簿的记录增量导入到黑名单中（批量任务）
     *
     * @return
     */
    Boolean incrementalUpdateBlackList();

}
