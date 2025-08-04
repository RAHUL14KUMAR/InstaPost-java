package com.instagram.post.instapost.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.post.instapost.Entity.UserEntity;

@Repository
public interface userRepo extends JpaRepository<UserEntity, Long>{
    boolean existsByEmail(String email);
}
