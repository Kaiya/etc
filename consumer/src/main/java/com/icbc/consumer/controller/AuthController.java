package com.icbc.consumer.controller;

import com.icbc.consumer.service.Auth;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kaiya Xiong
 * @date 8/27/18
 */
@RestController
public class AuthController {


    @RequestMapping(value = "/auth/{address}/{duration}", method = RequestMethod.GET)
    public String getReceiptByHash(@PathVariable("address") String address, @PathVariable("duration") String duration) throws Exception {
        return Auth.createJWT(address, Long.valueOf(duration));

    }
}
