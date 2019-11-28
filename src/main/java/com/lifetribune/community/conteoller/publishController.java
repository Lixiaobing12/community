package com.lifetribune.community.conteoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
   Controller类  允许接受前端的请求
   @GetMapping()
   @RequestParam()
 */
@Controller

public class publishController
{
    @GetMapping("/publish")
    public String index(){
        return "publish";
    }
}
