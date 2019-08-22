package com.example.ccps.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class MoneyCostVerify {
    public static boolean verifyMoneyCost(String money) {
        String pattern = "^(([1-9]{1}\\d{0,3})|([0]{1}))(\\.(\\d){0,2})?$";
        boolean isMatch = Pattern.matches(pattern, money);
        System.out.println(isMatch);
        System.out.println(money);
        return isMatch;
    }
}
