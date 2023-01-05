package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponseDTO;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Autowired //from spring 4.3, if the class is a spring bean and has only constructor, @Autowired can be omitted
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = convertDTOToEntity(postDTO);
        Post createdPost = postRepository.save(post);
        return convertEntityToDTO(createdPost);
    }

    @Override
    public PostResponseDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        //pagination and sorting

        //dynamically set sorting based on sortDir
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //set pageNo, pageSize and sortBy to a pageable object
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        //pass pageable object to findAll method, returns a Page<Post>
        Page<Post> posts = postRepository.findAll(pageable);
        //use getContent method to convert Page<Post> to List<Post>
        List<Post> postList = posts.getContent();

        //convert post entity to postDTO to return response
        List<PostDTO> postDTOS = postList.stream()
                .map(post -> convertEntityToDTO(post))
                .collect(Collectors.toList());

        //PostDTO response, to send extra info to client such as totalPages, pageNo, pageSize, lastPage, etc.
        //Page<Post> has default methods, such as getNumber, getSize. we can use it to set as page response
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setContent(postDTOS);
        postResponseDTO.setPageNo(posts.getNumber());
        postResponseDTO.setPageSize(posts.getSize());
        postResponseDTO.setTotalElements(posts.getTotalElements());
        postResponseDTO.setTotalPages(posts.getTotalPages());
        postResponseDTO.setLastPage(posts.isLast());

        return postResponseDTO;
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
/*      PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        postDTO.setComments(post.getComments());
 */     //instead of using above steps to convert entity to dto, use modelMapper
        return modelMapper.map(post, PostDTO.class);
    }

    private Post convertDTOToEntity(PostDTO postDTO){
        return modelMapper.map(postDTO, Post.class);
    }
}
