package com.example.wang.contriller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/")
    public String index(){
        return "index"; //当浏览器输入/时，会返回 /static/home.html的页面
    }

}
