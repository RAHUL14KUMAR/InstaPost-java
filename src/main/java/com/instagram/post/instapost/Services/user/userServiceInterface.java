package com.instagram.post.instapost.Services.user;

import com.instagram.post.instapost.Dto.profileDto;
import com.instagram.post.instapost.Dto.signUpReqDto;
import com.instagram.post.instapost.Dto.signUpRespDto;


public interface userServiceInterface {
    signUpRespDto addUserInDb(signUpReqDto user);
    profileDto getUserProfile(Long userId);
    boolean updateUserProfile(Long userId, Object profile);
}
