package com.ethon.springboot.test.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TalkModel {

    private Long id;

    private String type;

    private UserModel user;

    private LocalDateTime readDate;

    private LocalDateTime regDate;

}
