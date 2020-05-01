package com.exam.travel.controller;


import com.exam.travel.dto.PaginationDTO;
import com.exam.travel.dto.QuestionDTO;
import com.exam.travel.mapper.UserMapper;
import com.exam.travel.schedule.HotTagCache;
import com.exam.travel.service.NotificationService;
import com.exam.travel.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author w1586
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "tag", required = false) String tag)
    {
//
//        User user1 = (User) request.getSession().getAttribute("user");
//
//        UserExample example = new UserExample();
//        example.createCriteria().andAccountIdEqualTo(user1.getAccountId());
//        List<User> users = userMapper.selectByExample(example);
//        User user = users.get(0);

        PaginationDTO<QuestionDTO> paginationDTO = questionService.list(tag,search,page,size);
        List<String> tags = hotTagCache.getHots();

//        Long unreadCount = notificationService.unreadCount(user.getId());
//        model.addAttribute("unreadCount",unreadCount);
        model.addAttribute("pagination",paginationDTO);
        model.addAttribute("search",search);
        model.addAttribute("tags",tags);
        model.addAttribute("tag",tag);
        return "index";
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response)
    {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
