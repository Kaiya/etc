package com.icbc.provider.mapper;

import com.icbc.provider.model.Blacklist;
import com.icbc.provider.model.Register;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kaiya Xiong
 * @date 2019-08-15
 */
@Mapper
@Service
public interface BlacklistMapper {
    Blacklist selectByIdentityNumber(String identityNumber);

    int addBlacklist(Blacklist blacklist);

    List<Blacklist> selectAll();

    /**
     * 插入从登记簿增量查询到的数据
     * @param registers
     * @return
     */
    int insertIncrementalBlackList(List<Register> registers);
}
