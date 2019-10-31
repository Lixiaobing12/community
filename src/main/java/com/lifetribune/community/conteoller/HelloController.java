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

public class HelloController
{
    @GetMapping("/hello")
    public String hello(@RequestParam(name="name") String name,Model model){
        model.addAttribute("name",name);
        return "hello";
    }
}
