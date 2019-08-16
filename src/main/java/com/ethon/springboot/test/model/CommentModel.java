package com.ethon.springboot.test.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentModel {

    private Long id;

    private LocalDateTime regDate;

}
