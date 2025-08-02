package com.instagram.post.instapost.Dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class signUpDto {
    private Long id;
    private String username;

    @Email
    private String email;

    private String password;
}
