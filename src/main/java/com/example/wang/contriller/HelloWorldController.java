package com.example.wang.contriller;

import com.alibaba.fastjson.JSON;
import com.example.wang.daomain.CommonModel;
import com.example.wang.daomain.JsSdkModel;
import com.example.wang.daomain.MenuModel;
import com.example.wang.entity.Menu;
import com.example.wang.entity.WechatAccountConfig;
import com.example.wang.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class HelloWorldController {


    @Autowired
    public WechatAccountConfig wechatAccountConfig;

    @RequestMapping(value = "/verity", method = RequestMethod.GET)
    public void hello1(HttpServletRequest request, HttpServletResponse response) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        try (PrintWriter out = response.getWriter()) {
            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                out.write(echostr);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello world";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu() {
        MenuModel menuModel = new MenuModel();
        Menu menu =  menuModel.getMenu();
        String data= JSON.toJSONString(menu);
        String accessToken = CommonModel.getAccessToken(wechatAccountConfig);
        String result = menuModel.createMenu(accessToken, data, wechatAccountConfig);
        if ("0".equals(result)) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping(value = "/jssdk", method = RequestMethod.GET)
    public String  jSSDK_config(@RequestParam(value = "url") String url) {
        JsSdkModel jsSdkModel = new JsSdkModel(wechatAccountConfig);
        return jsSdkModel.initJssdk(url);
    }

}
