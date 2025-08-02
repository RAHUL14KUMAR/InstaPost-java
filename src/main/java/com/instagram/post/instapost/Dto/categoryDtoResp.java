package com.instagram.post.instapost.Dto;

import java.util.List;

import com.instagram.post.instapost.Entity.PostEntity;
import com.instagram.post.instapost.Entity.UserEntity;

public class categoryDtoResp {
    private Long id;

    private String categoryName;

    private List<PostEntity> posts;

    private List<UserEntity> users;
}
