package com.example.ccps.mapper;

import com.example.ccps.bean.Blacklist;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Mapper
@Service
public interface BlacklistMapper {
//    数据库查询操作
    @Select("select * from ccps_blacklist where identity_number = #{identity_number}")
    @Results(id = "blackMap", value = {
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(property = "identity_type", column = "identity_type", javaType = Integer.class),
            @Result(property = "identity_number", column = "identity_number", javaType = String.class),
            @Result(property = "date_banned", column = "date_banned", javaType = Timestamp.class),
            @Result(property = "reason_banned", column = "reason_banned", javaType = Integer.class)
    })
    public Blacklist getBlacklistByIdentity_number(String identity_number);

//    数据库插入操作
    @Insert("insert into ccps_blacklist value (#{name},#{identity_type},#{identity_number},#{date_banned},#{reason_banned})")
    public int insertBlacklist(String name, Integer identity_type, String identity_number, Timestamp date_banned, Integer reason_banned);

    @Select("select * from ccps_blacklist")
    @Results(id = "blacksMap", value = {
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(property = "identity_type", column = "identity_type", javaType = Integer.class),
            @Result(property = "identity_number", column = "identity_number", javaType = String.class),
            @Result(property = "date_banned", column = "date_banned", javaType = Timestamp.class),
            @Result(property = "reason_banned", column = "reason_banned", javaType = Integer.class)
    })
    public List<Blacklist> getAllBkackList();

}
