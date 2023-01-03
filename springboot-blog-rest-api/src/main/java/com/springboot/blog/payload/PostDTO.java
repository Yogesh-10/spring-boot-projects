package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
    private List<CommentDTO> comments;
}
