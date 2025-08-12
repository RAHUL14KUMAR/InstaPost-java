package com.instagram.post.instapost.Dto;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
public class postDtoReq {
    private String desc;
    private Set<Long> postCategory;
}

