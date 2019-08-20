package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.mapper.BlacklistMapper;
import com.icbc.provider.mapper.RegisterMapper;
import com.icbc.provider.model.Blacklist;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Kaiya Xiong
 * @date 2019-08-15
 */
@Service
@Component
public class BlacklistServiceImpl implements BlacklistService {

    @Autowired
    RegisterMapper registerMapper;
    @Autowired
    BlacklistMapper blacklistMapper;

    /**
     * @return
     */
    @Override
    public Boolean batchAddBlacklist() {
        //查询所有的登记簿记录
        Boolean flag = false;
        List<Register> registers = registerMapper.selectAll();
        if (registers != null) {
            for (Register r : registers) {
                //把从登记簿查询出来的每一条记录逐条导入到黑名单里
                Blacklist blacklist = new Blacklist();
                blacklist.setName(r.getName());
                blacklist.setIdentityType(r.getIdentityType());
                blacklist.setIdentityNumber(r.getIdentityNumber());
                blacklist.setDateBanned(r.getDateFailed());
                blacklist.setReasonBanned(1);//加入黑名单原因没有对应。。。
                blacklistMapper.addBlacklist(blacklist);
            }
            flag = true;
        }

        return flag;
    }
}
