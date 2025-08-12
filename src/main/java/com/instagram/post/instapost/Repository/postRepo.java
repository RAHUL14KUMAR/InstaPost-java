package com.instagram.post.instapost.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.post.instapost.Entity.PostEntity;

@Repository
public interface postRepo extends JpaRepository<PostEntity, Long> {
    
}
