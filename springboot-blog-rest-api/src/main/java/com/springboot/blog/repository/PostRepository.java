package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//No need to add @Repository and @Transactional, because it is provided in JPARepository by default
//NOTE: @Transactional(readonly=true), by default. if we need to change it, then we can use @Transactional in Repository layer
public interface PostRepository extends JpaRepository<Post, Long> {

    //spring data jpa - search post by title or description
    List<Post> searchPostByTitleContainingOrDescriptionContaining(String title, String description);

    //JPQL query - search post by title or description
    @Query(value = "SELECT p from Post p WHERE p.title LIKE CONCAT('%', :query, '%') " +
                   "OR p.description LIKE CONCAT('%', :query, '%')")
    List<Post> searchPostByTitleOrDescriptionJPQL(String query);

    //Native SQL Query - search post by title or description
    @Query(value = "SELECT * from posts p WHERE p.title LIKE CONCAT('%', :query, '%') " +
                   "OR p.description LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<Post> searchPostByTitleOrDescriptionNativeSQL(String query);

}
