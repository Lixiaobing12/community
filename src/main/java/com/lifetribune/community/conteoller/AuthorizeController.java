package com.lifetribune.community.conteoller;

import com.lifetribune.community.dto.AccessTokenDTO;
import com.lifetribune.community.dto.GithubUser;
import com.lifetribune.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name ="code")String code,
                           @RequestParam(name = "state")String state
                           ){
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(client_Secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubprovider.getAccessToken(accessTokenDTO);
        GithubUser user = githubprovider.getUser(accessToken);
        System.out.println( user.getName());
        return "index";
    }
}
