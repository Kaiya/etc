package com.icbc.provider.service;

import com.icbc.provider.model.Register;

/**
 * @author Kaiya Xiong
 * @date 2019-08-15
 */
public interface RegisterService {


    /**
     * 扣款失败，记录到登记簿
     *
     * @param register
     * @return 是否写入成功
     */
    Boolean gotoDarkroom(Register register);

    /**
     * 批量导出到文件，通过ftp和ccps同步（批量任务）
     *
     * @param path csv文件路径
     * @return 是否导出成功
     */

    Boolean batchExportToFile(String path);
}
