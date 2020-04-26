package com.exam.travel.dto;

import lombok.Data;

/**
 * @Author w1586
 * @Date 2020/3/23 12:13
 * @Cersion 1.0
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
