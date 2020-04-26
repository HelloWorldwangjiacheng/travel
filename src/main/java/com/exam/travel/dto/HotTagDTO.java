package com.exam.travel.dto;

import lombok.Data;

/**
 * @Author w1586
 * @Date 2020/4/26 21:45
 * @Cersion 1.0
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
