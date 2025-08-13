package com.instagram.post.instapost.Controllers;

// import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.post.instapost.Dto.ResponseDto;
import com.instagram.post.instapost.Dto.loginResDto;
import com.instagram.post.instapost.Dto.profileDto;
import com.instagram.post.instapost.Dto.signUpReqDto;
import com.instagram.post.instapost.Dto.signUpRespDto;
import com.instagram.post.instapost.Entity.UserEntity;
import com.instagram.post.instapost.Exeption.UserException;
import com.instagram.post.instapost.Services.user.Implementation.userService;

import jakarta.validation.Valid;




@RestController
public class UserController {
    private userService userserv;
    public UserController(userService userserv) {
        this.userserv = userserv;
    }

    @PostMapping("/add-user")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody signUpReqDto entity) {
        //TODO: process POST request
        try {
            signUpRespDto result=userserv.addUserInDb(entity);
            if(result==null){
                throw new UserException("User already exists with this email");
            }
            ResponseDto resp = new ResponseDto();
            resp.setAnswer(result);
            resp.setMessage("user added successfully");
            return ResponseEntity.status(200).body(resp);
        } catch (Exception e) {
            ResponseDto resp = new ResponseDto();
            resp.setAnswer(null);
            resp.setMessage(e.getMessage());
            return ResponseEntity.status(403).body(resp);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseDto> getUserProfileDetails(Authentication authentication) {
        try{
            UserEntity user = (UserEntity) authentication.getPrincipal();
            System.out.println("user form controller"+user);
            profileDto result= userserv.getUserProfile(user.getId());
            ResponseDto resp = new ResponseDto();
            resp.setAnswer(result);
            resp.setMessage("User profile fetched successfully");
            return ResponseEntity.status(200).body(resp);

        }catch(Exception e){
            ResponseDto resp = new ResponseDto();
            resp.setAnswer(null);
            resp.setMessage(e.getMessage());
            return ResponseEntity.status(404).body(resp);
        }
    }

    @PutMapping("/user/{id}")
    public boolean userUpdateProfile(@PathVariable Long id, @RequestBody Long[] entity) {
        return userserv.updateUserInterest(id, entity);
    }

    @PostMapping("/login")
    public loginResDto loginServiceCalled(@RequestBody signUpReqDto user) {
        return userserv.login(user);
    }
    
    
    
}
