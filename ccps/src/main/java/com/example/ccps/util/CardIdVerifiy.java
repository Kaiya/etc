package com.example.ccps.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 校验卡号为16位的纯数字
 */
public class CardIdVerifiy {
    public static Map<String, Object> verifyCardId(String cardId) {
        Map map = new HashMap();
        map.put("msg","卡号合法");
        map.put("valid",true);
        if (cardId.length() != 16) {
            map.put("msg","卡号位数不正确");
            map.put("valid",false);
        }
        for (int i=0; i<cardId.length(); i++) {
            if (cardId.charAt(i)<'0' || cardId.charAt(i)>'9') {
                map.put("msg","卡号只能包含数字");
                map.put("valid",false);
            }
        }
        System.out.println(map.get("msg"));
        System.out.println(map.get("valid"));
        return map;
    }
}
