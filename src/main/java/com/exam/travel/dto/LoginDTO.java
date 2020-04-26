package com.exam.travel.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author w1586
 * @Date 2020/3/16 15:15
 * @Cersion 1.0
 */
@Data
public class LoginDTO {


    private String userName ;

    @NotNull
    private String accountId;


    private String userPassword;


    private String phoneCode;

    @Override
    public String toString() {
        return "LoginDTO{" +
                "userName='" + userName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                '}';
    }
}
