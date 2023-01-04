package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PostDTO {
    private Long id;

    @NotEmpty(message = "Title Should not be empty")
    @Size(min = 5, message = "Title should have minimum 5 characters")
    private String title;

    @NotEmpty(message = "Description Should not be empty")
    @Size(min = 10, message = "Description should have minimum 10 characters")
    private String description;

    @NotEmpty(message = "Content Should not be empty")
    private String content;

    private List<CommentDTO> comments;
}
