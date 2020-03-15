package com.exam.travel.mapper;


import com.exam.travel.model.Question;

import java.util.List;

/**
 * @author w1586
 */
public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);
//
//    Integer countBySearch(QuestionQueryDTO questionQueryDTO);
//
//    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}