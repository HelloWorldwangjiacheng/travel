package com.exam.travel.controller;


import com.exam.travel.dto.CommentDTO;
import com.exam.travel.dto.QuestionDTO;
import com.exam.travel.enums.CommentTypeEnum;
import com.exam.travel.service.CommentService;
import com.exam.travel.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author w1586
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

//    @ApiOperation("得到某个确切id的question")
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model)
    {
        //通过questionService去调用questionMapper
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
