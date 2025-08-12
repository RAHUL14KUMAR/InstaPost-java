package com.instagram.post.instapost.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class userPostDto {
    private Long podtId;
    private String desc;
}
