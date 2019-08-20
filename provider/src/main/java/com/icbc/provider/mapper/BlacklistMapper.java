package com.icbc.provider.mapper;

import com.icbc.provider.model.Blacklist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * @author Kaiya Xiong
 * @date 2019-08-15
 */
@Mapper
@Service
public interface BlacklistMapper {
    Blacklist selectByIdentityNumber(String identityNumber);

    int addBlacklist(Blacklist blacklist);
}
