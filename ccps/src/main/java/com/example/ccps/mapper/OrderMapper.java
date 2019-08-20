package com.example.ccps.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.ccps.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface OrderMapper {
    List<Order> select(Order order);

    int insert(Order order);       //mapper.xml对应这儿的方法名。



    int delete(String order_id);

    int update(Order order);


}
