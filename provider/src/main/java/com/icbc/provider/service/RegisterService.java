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
}
