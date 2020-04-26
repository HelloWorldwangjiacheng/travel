package com.exam.travel.controller;


import com.exam.travel.dto.AccessTokenDTO;
import com.exam.travel.dto.GithubUser;
import com.exam.travel.model.User;
import com.exam.travel.provider.GithubProvider;
import com.exam.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author w1586
 */
@Controller
public class GithubAuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String githubCallback(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state,
                                 HttpServletRequest request,
                                 HttpServletResponse response
                                 )
    {
        AccessTokenDTO accessTokenDTO1 = new AccessTokenDTO();
        accessTokenDTO1.setClient_id(clientId);
        accessTokenDTO1.setClient_secret(clientSecret);
        accessTokenDTO1.setCode(code);
        accessTokenDTO1.setRedirect_uri(redirectUri);
        accessTokenDTO1.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO1);
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(githubUser.getName());
        if(githubUser != null && githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setUserName(githubUser.getName());
            //将id强制转换为String类型
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());

            userService.createOrUpdate(user);

//            userMapper.githubInsert(user);
            Cookie cookie = new Cookie("token",token);
            response.addCookie(cookie);

            // 登录成功，写cookie和session
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            //登录失败，重新登录
            return "redirect:/";
        }

    }
}
