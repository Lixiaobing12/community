package com.lifetribune.community.conteoller;

import com.lifetribune.community.mapper.QuestionMapper;
import com.lifetribune.community.mapper.UserMapper;
import com.lifetribune.community.model.Question;
import com.lifetribune.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
   Controller类  允许接受前端的请求
   @GetMapping()
   @RequestParam()
 */
@Controller
public class publishController
{
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String index(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("tag") String tag,
        HttpServletRequest request,
        Model model
    ){
        User user = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies)
        {
            if (cookie.getName().equals("token")){
                String token =cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question =new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        questionMapper.create(question);
        return "redirect:/";
    }
}
