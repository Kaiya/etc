package com.example.ccps.controller;

import com.example.ccps.imp.Auth;
import com.example.ccps.model.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @RequestMapping(value = "/auth/{uid}/{duration}", method = RequestMethod.GET)
    public Response getReceiptByHash(@PathVariable("uid") String uid, @PathVariable("duration") String duration){
        Response response = new Response();
        String token = null;
        try {
            token = Auth.createJWT(uid, Long.valueOf(duration));
            response.code = 1;
            response.msg="获取Token成功";
            response.result = token;
        } catch (Exception e) {
            e.printStackTrace();
            response.code = 0;
            response.msg = "获取Token失败";
        }

        return response;
    }
}
