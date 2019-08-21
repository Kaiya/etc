package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.mapper.RegisterMapper;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Kaiya Xiong
 * @date 2019-08-15
 */
@Service
@Component
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RegisterMapper registerMapper;

    @Override
    public Boolean gotoDarkroom(Register register) {
        Boolean flag = false;
        if (register != null) {
            int result = registerMapper.addPayFailed(register);
            if (result >= 0) {
                flag = true;
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

}
