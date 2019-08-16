package com.ethon.springboot.test.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SkinModel {

    private Long id;

    private UserModel user;

    private LocalDateTime regDate;

}
