package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<CommentDTO> getAllCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDTO> commentDTOS = comments.stream()
                .map(comment -> convertEntityToDTO(comment))
                .collect(Collectors.toList());

        return commentDTOS;
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        return convertEntityToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());

        Comment updatedComment = commentRepository.save(comment);
        return convertEntityToDTO(updatedComment);
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
