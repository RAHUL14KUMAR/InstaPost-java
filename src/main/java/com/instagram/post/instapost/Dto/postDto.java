package com.instagram.post.instapost.Dto;

import java.util.Set;

import com.instagram.post.instapost.Entity.CategoryEntity;
import com.instagram.post.instapost.Entity.UserEntity;

import lombok.Data;

@Data
public class postDto {
    private Long id;

    private String desc;

    private Set<CategoryEntity> postCategory;

    private UserEntity user;
}
