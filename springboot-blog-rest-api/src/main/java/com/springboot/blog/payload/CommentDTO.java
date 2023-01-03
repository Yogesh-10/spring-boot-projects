package com.springboot.blog.payload;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String name;
    private String email;
    private String body;
}
