package com.example.wang.util;

import com.alibaba.fastjson.JSON;
import com.example.wang.entity.Result;

public class Message {


    public static String success(String data) {
        Result result = new Result();
        result.setResult(data);
        result.setErrcode("0");
        return JSON.toJSONString(result);
    }

    public static String fail(String errormsg) {
        Result result = new Result();
        result.setErrmsg(errormsg);
        result.setErrcode("-1");
        return JSON.toJSONString(result);
    }

}
