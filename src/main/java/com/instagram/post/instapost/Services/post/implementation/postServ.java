package com.instagram.post.instapost.Services.post.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.instagram.post.instapost.Dto.categoryDto;
import com.instagram.post.instapost.Dto.postDto;
import com.instagram.post.instapost.Dto.postDtoReq;
import com.instagram.post.instapost.Dto.signUpRespDto;
import com.instagram.post.instapost.Dto.updatePostReq;
import com.instagram.post.instapost.Entity.CategoryEntity;
import com.instagram.post.instapost.Entity.PostEntity;
import com.instagram.post.instapost.Entity.UserEntity;
import com.instagram.post.instapost.Repository.categoryRepo;
import com.instagram.post.instapost.Repository.postRepo;
import com.instagram.post.instapost.Repository.userRepo;
import com.instagram.post.instapost.Services.post.postService;

@Service
public class postServ implements postService {
    private postRepo postRepository;
    private userRepo userRepository;
    private categoryRepo categoryRepository;

    public postServ(postRepo repo, userRepo rep, categoryRepo re){
        this.postRepository=repo;
        this.userRepository=rep;
        this.categoryRepository=re;
    }

    public postDto createPost(Long userId, postDtoReq req){
        UserEntity userExist = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User with the id does not exist"));

        PostEntity post = new PostEntity();
        post.setUser(userExist);
        post.setPostDesc(req.getDesc());

        Set<CategoryEntity> categories = new HashSet<>();
        for (Long id : req.getPostCategory()) {
            CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with this id not found: " + id));
            categories.add(category);
        }

        post.setPostCategory(categories);
        for (CategoryEntity act : categories) {
            act.getPosts().add(post);
        }

        postRepository.save(post);

        // Convert to postDto
        return new postDto(
            post.getId(),
            post.getPostDesc(),
            categories.stream()
                .map(cat -> new categoryDto(cat.getId(), cat.getCategoryName()))
                .collect(Collectors.toSet()),

            new signUpRespDto(userExist.getId(),userExist.getUsername(),userExist.getEmail(),userExist.getPassword())
        );
    }

    public List<postDto> getAllPost(){
        List<PostEntity>exi=postRepository.findAll();
        return exi.stream().map(post -> new postDto(post.getId(), post.getPostDesc(), post.getPostCategory().stream().map(cat->new categoryDto(cat.getId(),cat.getCategoryName())).collect(Collectors.toSet()), new signUpRespDto(post.getUser().getId(), post.getUser().getUsername(), post.getUser().getEmail(), post.getUser().getPassword()) )).toList();
    }

    public postDto getPostById(Long postId){
        PostEntity post= postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("post doesnot exists by id"));

        // Map CategoryEntity → categoryDto
        Set<categoryDto> categoryDtos = post.getPostCategory()
        .stream()
        .map(cat -> new categoryDto(cat.getId(), cat.getCategoryName()))
        .collect(Collectors.toSet());

        // Map UserEntity → signUpRespDto
        UserEntity userEntity = post.getUser();
        signUpRespDto userDto = new signUpRespDto(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getEmail(),
            userEntity.getPassword()
        );

        return new postDto(post.getId(),post.getPostDesc(),categoryDtos,userDto);
        // return null;
    }

    public boolean updatePost(Long userId,updatePostReq req,Long postId){
        PostEntity postExist=postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("post not exist with this id"));

        if(postExist!=null && postExist.getUser().getId()==userId){
            postExist.setPostDesc(req.getDesc());
        }else{
            return false;
        }
        postRepository.save(postExist);
        return true;
    }

    public boolean deletePost(Long postId){
        PostEntity res=postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("post with id not found"));
        if(res!=null){
            Long userId=res.getUser().getId();
            UserEntity userExist=userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("user not found"));
            userExist.getPosts().remove(res);
            postRepository.delete(res);
            return true;
        }
        return false;
    }
 
}
