package com.example.wang.daomain;

import com.alibaba.fastjson.JSON;
import com.example.wang.entity.AccessToken;
import com.example.wang.entity.WechatAccountConfig;
import com.example.wang.util.HttpUtil;

public class CommonModel {


    /**
     * 这个封装结构不太好
     * 获取access_token
     * access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
     */
    public static String getAccessToken(WechatAccountConfig wechatAccountConfig) {
        AccessToken accessToken;
        String token_url = wechatAccountConfig.getAccessTokenUrl();
        String requestUrl = token_url.replace("APPID", wechatAccountConfig.getAppid()).replace("APPSECRET",wechatAccountConfig.getAppsecret());
        String result = HttpUtil.doGetstr(requestUrl);

        accessToken = JSON.parseObject(result, AccessToken.class);
        if (accessToken != null) {
            return accessToken.getAccess_token();
        } else {
            return null; // 这里返回了null需要后续处理
        }
    }

}
