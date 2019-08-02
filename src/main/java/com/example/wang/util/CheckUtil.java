package com.example.wang.util;

import java.util.Arrays;
import java.util.UUID;

public class CheckUtil {


    public static boolean checkSignature(String signature,String timestamp,String nonce) {
        String[] str = new String[]{ Constant.wechatAccountConfig.getToken(), timestamp, nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }

    /**
     * 时间戳
     */
    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 生成前面的随机春
     */
    public static String createNocestr() {
        return UUID.randomUUID().toString();
    }

}
