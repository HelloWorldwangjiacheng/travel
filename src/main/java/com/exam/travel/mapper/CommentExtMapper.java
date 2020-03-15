package com.exam.travel.mapper;


import com.exam.travel.model.Comment;

/**
 * @author w1586
 */
public interface CommentExtMapper {
    /**
     * 增加评论数
     * @param record
     * @return
     */
    int incCommentCount(Comment record);
}