package com.icbc.provider.service;

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

    /**
     * 批量导出到文件，通过ftp和ccps同步（批量任务）
     *
     * @param path csv文件路径
     * @return 是否导出成功
     */

    Boolean batchExportToFile(String path);

}
