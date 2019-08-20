package com.icbc.provider.mapper;

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
public interface RegisterMapper {
    Register selectByOrderId(String orderId);

    int addPayFailed(Register register);

    List<Register> selectAll();
}
