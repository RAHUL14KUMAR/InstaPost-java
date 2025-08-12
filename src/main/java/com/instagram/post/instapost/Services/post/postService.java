package com.instagram.post.instapost.Services.post;

import java.util.List;

import com.instagram.post.instapost.Dto.postDto;
import com.instagram.post.instapost.Dto.postDtoReq;
import com.instagram.post.instapost.Dto.updatePostReq;

public interface postService {
    postDto createPost(Long userId,postDtoReq req);
    List<postDto> getAllPost();
    postDto getPostById(Long postId);
    boolean updatePost(Long userId, updatePostReq req,Long postId);
    boolean deletePost(Long PostId);
}
