package com.example.ccps.controller;

import com.example.ccps.bean.Blacklist;
import com.example.ccps.mapper.BlacklistMapper;
import com.example.ccps.bean.Blacklist;
import com.example.ccps.mapper.BlacklistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class BlacklistController {

    @Autowired
    BlacklistMapper blacklistMapper;

    @GetMapping("/blacklist/{identity_number}")
    public Blacklist getBlackliskMapper(@PathVariable("identity_number") String identity_number) {
        return blacklistMapper.getBlacklistByIdentity_number(identity_number);
    }

    @GetMapping("/blacklist")
    public int insertBlacklist(@RequestParam("name")String name, @RequestParam("identity_type")Integer identity_type,
                               @RequestParam("identity_number")String identity_number,
                               @RequestParam("date_banned")Timestamp date_banned, @RequestParam("reason_banned") Integer reason_banned){
        return blacklistMapper.insertBlacklist(name, identity_type, identity_number,date_banned, reason_banned);
    }

    @GetMapping("/getAll")
    public List<Blacklist> getAllBlackList(){
        List<Blacklist> blacklists = blacklistMapper.getAllBkackList();
        for (Blacklist black: blacklists
             ) {
            black.setCreateTime(black.getDate_banned().getTime());
        }
        return blacklists;
    }

}
