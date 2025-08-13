package com.instagram.post.instapost.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.post.instapost.Dto.postDto;
import com.instagram.post.instapost.Dto.postDtoReq;
import com.instagram.post.instapost.Dto.updatePostReq;
import com.instagram.post.instapost.Services.post.postService;




@RestController
public class postController {
    @Autowired
    private postService postServ;

    @PostMapping("/create-post/{userId}")
    public postDto createPost(@PathVariable Long userId, @RequestBody postDtoReq req) {
        System.out.println("req "+req);
        return postServ.createPost(userId, req);
    }

    @GetMapping("/post/{postId}")
    public postDto getPostById(@PathVariable Long postId) {
        return postServ.getPostById(postId);
    }

    @GetMapping("/posts")
    public List<postDto> getAllPosts() {
        return postServ.getAllPost();
    }

    @PutMapping("/post/{id}")
    public boolean putPost(@PathVariable Long id, @RequestBody updatePostReq req,Long postId) {
        return postServ.updatePost(id,req,postId);
    }

    @DeleteMapping("/post/{postId}")
    public boolean deletePost(@PathVariable Long postId){
        return postServ.deletePost(postId);
    }
}
