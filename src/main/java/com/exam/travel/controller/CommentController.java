package com.exam.travel.controller;


import com.exam.travel.dto.CommentCreateDTO;
import com.exam.travel.dto.CommentDTO;
import com.exam.travel.enums.CommentTypeEnum;
import com.exam.travel.mapper.UserMapper;
import com.exam.travel.model.Comment;
import com.exam.travel.model.User;
import com.exam.travel.model.UserExample;
import com.exam.travel.result.CodeMsg;
import com.exam.travel.result.Result;
import com.exam.travel.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentService commentService;

    //用requestBody接收json格式的数据
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user1 = (User) request.getSession().getAttribute("user");
        if (user1==null){
            return Result.error(CodeMsg.NO_LOGIN);
        }

        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user1.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        User user = users.get(0);

        if (user == null) {
            //向内封装
            return Result.error(CodeMsg.NO_LOGIN);
        }
//        commentCreateDTO == null || commentCreateDTO.getContent() == null || commentCreateDTO.getContent() == ""
        //在org.apache.commons：commons-lang3下面的StringUntils方法类的使用效果等同于commentCreateDTO.getContent() == null || commentCreateDTO.getContent() == ""
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return Result.error(CodeMsg.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
//        commentMapper.insert(comment);
        commentService.insert(comment, user);

        Map<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("message", "成功");
        return Result.success("成功");
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public Result<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return Result.success(commentDTOS);
    }

}
