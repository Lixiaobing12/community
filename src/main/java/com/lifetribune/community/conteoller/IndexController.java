package com.lifetribune.community.conteoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
   Controller类  允许接受前端的请求
   @GetMapping()
   @RequestParam()
 */
@Controller

public class IndexController
{
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
