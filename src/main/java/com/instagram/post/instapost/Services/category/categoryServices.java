package com.instagram.post.instapost.Services.category;

import com.instagram.post.instapost.Dto.categoryDto;
import com.instagram.post.instapost.Dto.categoryDtoResp;

public interface categoryServices {
    categoryDto createCategory(String categoryName);

    categoryDto getCategoryById(Long id);

    categoryDto updateCategory(Long id, categoryDto categoryDto);

    categoryDtoResp getallInformationAboutCategory(Long id);
}
