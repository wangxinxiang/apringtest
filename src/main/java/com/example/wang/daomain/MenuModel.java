package com.example.wang.daomain;

import com.alibaba.fastjson.JSON;
import com.example.wang.entity.*;
import com.example.wang.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuModel {


    public Menu getMenu() {
        Menu menu = new Menu();
        ViewButton button1 = new ViewButton();
        button1.setName("博客");
        button1.setType("view");
        button1.setUrl("https://www.jianshu.com/u/eef157b997cc");

        ViewButton button2 = new ViewButton();
        button2.setName("github");
        button2.setType("view");
        button2.setUrl("https://github.com/wangxinxiang");


        ClickButton button31 = new ClickButton();
        button31.setName("关于我");
        button31.setType("click");
        button31.setKey("me");

        Button button3 = new Button();
        button3.setName("更多");
        button3.setSub_buttom(new Button[]{button31});

        menu.setButtons(new Button[]{button1, button2, button3});

        return menu;
    }


    /**
     * 创建菜单
     */
    public  String createMenu(String token,String menu, WechatAccountConfig wechatAccountConfig) {
        String url = wechatAccountConfig.getCreateMenuUrl();
        String requestUrl = url.replace("ACCESS_TOKEN", token);
        String result = HttpUtil.doPoststr(requestUrl, menu);
        Result result1 = JSON.parseObject(result, Result.class);
        return result1 != null ? result1.getErrcode() : null;
    }

}
