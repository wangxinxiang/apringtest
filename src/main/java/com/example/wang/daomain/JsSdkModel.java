package com.example.wang.daomain;

import com.alibaba.fastjson.JSON;
import com.example.wang.entity.JsapiTicket;
import com.example.wang.entity.WechatAccountConfig;
import com.example.wang.util.CheckUtil;
import com.example.wang.util.HttpUtil;
import com.example.wang.util.Message;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;

public class JsSdkModel {

    private WechatAccountConfig wechatAccountConfig;

    public JsSdkModel(WechatAccountConfig wechatAccountConfig) {
        this.wechatAccountConfig = wechatAccountConfig;
    }

    /**
     * 获取调用微信jssdk的签名
     * @param url 调用的网页
     */
    public String initJssdk(String url) {
        String accessToken = CommonModel.getAccessToken(wechatAccountConfig);
        if (accessToken == null) {
            return Message.fail("accessToken获取失败");
        }
        String ticket = getJsapiTicket(accessToken);
        if (ticket == null)  {
            return Message.fail("ticket获取失败");
        }
        try {
            HashMap<String, String> sign = jsSDK_Sign(url, ticket);
            return Message.success(JSON.toJSONString(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Message.fail("签名获取失败");
    }

    private String getJsapiTicket(String access_token) {
        JsapiTicket accessToken;
        String token_url = wechatAccountConfig.getJsapiTicket();
        String requestUrl = token_url.replace("ACCESS_TOKEN", access_token);
        String result = HttpUtil.doGetstr(requestUrl);

        accessToken = JSON.parseObject(result, JsapiTicket.class);
        if (accessToken != null) {
            return accessToken.getTicket();
        } else {
            return null; // 这里返回了null需要后续处理
        }
    }

    private HashMap<String, String> jsSDK_Sign(String url, String jsapi_ticket) throws Exception {
        String nonce_str = CheckUtil.createNocestr();
        String timestamp = CheckUtil.createTimestamp();
        // 注意这里参数名必须全部小写，且必须有序
        String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
                + "&timestamp=" + timestamp + "&url=" + url;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string1.getBytes(StandardCharsets.UTF_8));
        String signature = byteToHex(crypt.digest());
        HashMap<String, String> jssdk = new HashMap<String, String>();
        jssdk.put("appId", wechatAccountConfig.getAppid());
        jssdk.put("timestamp", timestamp);
        jssdk.put("nonceStr", nonce_str);
        jssdk.put("signature", signature);
        return jssdk;

    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
