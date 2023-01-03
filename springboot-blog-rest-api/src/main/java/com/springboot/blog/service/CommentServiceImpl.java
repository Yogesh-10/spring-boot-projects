package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDTO createComment(Long postId ,CommentDTO commentDTO) {
        Comment comment = convertDTOToEntity(commentDTO);

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("post", "id", postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

        return convertEntityToDTO(newComment);
    }

    private CommentDTO convertEntityToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        return commentDTO;
    }

    private Comment convertDTOToEntity(CommentDTO commentDTO){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        return comment;
    }
}
