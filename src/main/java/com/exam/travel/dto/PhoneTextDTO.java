package com.exam.travel.dto;

import lombok.Data;

/**
 * @Author w1586
 * @Date 2020/3/16 23:33
 * @Cersion 1.0
 */
@Data
public class PhoneTextDTO {
    private Boolean success;
    private String phoneCode;
    private String errorMsg;

    @Override
    public String toString() {
        return "PhoneTextDTO{" +
                "success=" + success +
                ", phoneCode='" + phoneCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
