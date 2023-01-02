package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    @Autowired //from spring 4.3, if the class is a spring bean and has only field to inject, @Autowired can be omitted
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = convertDTOToEntity(postDTO);
        Post createdPost = postRepository.save(post);
        return convertEntityToDTO(createdPost);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();
        return allPosts.stream()
                .map(post -> convertEntityToDTO(post))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("post", "id", id));

        return convertEntityToDTO(post);
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("post", "id", id));

        //if it is not null and not empty then only update
        if (Objects.nonNull(postDTO.getTitle()) &&
                !"".equalsIgnoreCase(postDTO.getTitle())){
            post.setTitle(postDTO.getTitle());
        }
        if (Objects.nonNull(postDTO.getContent()) &&
                !"".equalsIgnoreCase(postDTO.getContent())){
            post.setContent(postDTO.getContent());
        }
        if (Objects.nonNull(postDTO.getDescription()) &&
                !"".equalsIgnoreCase(postDTO.getDescription())){
            post.setDescription(postDTO.getDescription());
        }
        Post updatedPost = postRepository.save(post);

        return convertEntityToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("post", "id", id));

        postRepository.delete(post);
    }

    private PostDTO convertEntityToDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());

        return postDTO;
    }

    private Post convertDTOToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        return post;
    }
}
