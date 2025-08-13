package com.instagram.post.instapost.Dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class loginResDto {
    private Long UserId;
    
    private String username;

    @Email(message="invalid email format")
    private String email;

    private String token;
}
