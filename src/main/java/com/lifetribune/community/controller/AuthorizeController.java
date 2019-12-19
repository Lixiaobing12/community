package com.lifetribune.community.controller;

import com.lifetribune.community.dto.AccessTokenDTO;
import com.lifetribune.community.dto.GithubUser;
import com.lifetribune.community.mapper.UserMapper;
import com.lifetribune.community.model.User;
import com.lifetribune.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController
{
    @Autowired
    private GithubProvider githubprovider;
    @Autowired
    private AccessTokenDTO accessTokenDTO;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String client_Secret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name ="code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response){
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(client_Secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);

        String accessToken = githubprovider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubprovider.getUser(accessToken);
        if(githubUser !=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setBio(githubUser.getBio());

            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            //使用了redirect：把地址全部去掉，重定向到页面
            return "redirect:/";
        }else{
            //登录失败，重新登录
            return "redirect:/";
        }
    }
    
}
