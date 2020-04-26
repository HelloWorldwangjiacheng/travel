package com.exam.travel.dto;

import lombok.Data;

/**
 * @author w1586
 */
@Data
public class AccessTokenDTO {
    private String client_id ;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
