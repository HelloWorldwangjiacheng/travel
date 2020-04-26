package com.exam.travel.dto;

import lombok.Data;

@Data
public class FileUploadDTO {
    private int success;
    private String message;
    private String url;
}
