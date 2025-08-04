package com.instagram.post.instapost.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class categoryDto {
    private Long id;
    private String categoryName;
}
