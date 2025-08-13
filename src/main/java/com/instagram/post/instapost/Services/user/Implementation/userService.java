package com.instagram.post.instapost.Services.user.Implementation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.instagram.post.instapost.Dto.categoryDto;
import com.instagram.post.instapost.Dto.loginResDto;
import com.instagram.post.instapost.Dto.profileDto;
import com.instagram.post.instapost.Dto.signUpReqDto;
import com.instagram.post.instapost.Dto.signUpRespDto;
import com.instagram.post.instapost.Dto.userPostDto;
import com.instagram.post.instapost.Entity.CategoryEntity;
import com.instagram.post.instapost.Entity.UserEntity;
import com.instagram.post.instapost.Repository.categoryRepo;
import com.instagram.post.instapost.Repository.userRepo;
import com.instagram.post.instapost.Services.user.userServiceInterface;
import com.instagram.post.instapost.security.JwtUtil;

@Service
public class userService implements userServiceInterface {
    private userRepo userRepository;
    private categoryRepo categoryRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtutils;

    public userService(userRepo userRepository,
                       categoryRepo categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public signUpRespDto addUserInDb(signUpReqDto user) {
        // first check is user is already present in db
        boolean exist = userRepository.existsByEmail(user.getEmail());
        if(exist){
            return null;
        }
        
        // Create a new UserEntity from signUpReqDto
        UserEntity newUser=new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encoder.encode(user.getPassword()));

        // add newUser to db
        UserEntity savedUser = userRepository.save(newUser);

        // Create and return signUpRespDto
        return new signUpRespDto(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getPassword()
        );
    }
    @Override
    public profileDto getUserProfile(Long userId) {
        // Implementation for fetching user profile
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        profileDto profile=new profileDto();
        profile.setId(userId);
        profile.setUsername(user.getUsername());
        profile.setEmail(user.getEmail());
        if(user.getPosts() != null) {
            profile.setPosts(
                user.getPosts()
                    .stream()
                    .map(post -> new userPostDto(post.getId(), post.getPostDesc()))
                    .collect(Collectors.toList())
            );

        } else {
            profile.setPosts(null);
        }

        if(user.getUserInterestInCategory() != null) {
            Set<categoryDto> categories = user.getUserInterestInCategory().stream().map(cat -> {
                // System.out.println(cat);
                categoryDto dto = new categoryDto(cat.getId(),cat.getCategoryName());
                // System.out.println("cat->"+cat);
                return dto;
            }).collect(Collectors.toSet());

            profile.setUserInterestInCategory(categories);
        } else {
            profile.setUserInterestInCategory(null);
        }

        System.out.println("User profile fetched successfully for user ID: " + profile);
        return profile;
    }
    @Override
    public boolean updateUserInterest(Long userId, Long[] profile) {
        // user is now adding the interest in the category
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Set<CategoryEntity> categories=new HashSet<>();

        for(Long id:profile){
            CategoryEntity category=categoryRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Category with this id not found: "+id));
            categories.add(category);
        }

        user.setUserInterestInCategory(categories);

        for(CategoryEntity cat:categories){
            cat.getUsers().add(user);
        }
        userRepository.save(user);

        return true;
    }

    public loginResDto login(signUpReqDto user){
        System.out.println("user.getemail "+user.getEmail());
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword()
        ));
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();
        System.out.println("userDetails for login result->"+userDetails);


        String token= jwtutils.createToken(userDetails);
        System.out.println("token "+token);


        return new loginResDto(userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),token);
    }
}

