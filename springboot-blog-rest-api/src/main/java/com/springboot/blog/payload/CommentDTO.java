package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotEmpty(message = "Comment Body should not be empty")
    @Size(min = 10, message = "Comment should have minimum 10 characters")
    private String body;
}
