package com.springboot.blog.service;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponseDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponseDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO getPost(Long id);
    PostDTO updatePost(Long id, PostDTO postDTO);
    void deletePost(Long id);
}
