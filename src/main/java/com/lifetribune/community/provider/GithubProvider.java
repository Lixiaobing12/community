package com.lifetribune.community.provider;

import com.lifetribune.community.dto.AccessTokenDTO;
import com.lifetribune.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;


import java.io.IOException;


@Component
/*
 bean管理
 */
public class GithubProvider
{
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
               String string= response.body().string();
               String token = string.split("&")[0].split("=")[1];
               return token;
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        return  null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
        try
        {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
    /*JSON.parseObject 作用：把string类型的一个JSON对象，自动转换解析成java类的对象*/
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            //if (githubUser.getName() == null) System.out.println("未设置名字");
            return githubUser;
        }
        catch (IOException e)
        {
        }
        return null;
    }
}



