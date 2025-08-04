package com.instagram.post.instapost.Dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class signUpRespDto {
    private Long id;
    private String username;

    private String email;

    private String password;
}
