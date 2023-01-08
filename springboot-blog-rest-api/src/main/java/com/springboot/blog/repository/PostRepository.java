package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//No need to add @Repository and @Transactional, because it is provided in JPARepository by default
//NOTE: @Transactional(readonly=true), by default. if we need to change it, then we can use @Transactional in Repository layer
public interface PostRepository extends JpaRepository<Post, Long> {
}
