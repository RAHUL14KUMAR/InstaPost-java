package com.instagram.post.instapost.Dto;

import java.util.List;
import java.util.Set;

import com.instagram.post.instapost.Entity.PostEntity;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class profileDto {
    private Long id;
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    private Set<Long> userInterestInCategory;

    private List<PostEntity> posts;
}
