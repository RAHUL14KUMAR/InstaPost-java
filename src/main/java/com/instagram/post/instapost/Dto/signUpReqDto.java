package com.instagram.post.instapost.Dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class signUpReqDto {
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    private String password;
}
