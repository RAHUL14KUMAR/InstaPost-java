package com.instagram.post.instapost.Services.category.Implementation;

import org.springframework.stereotype.Service;

import com.instagram.post.instapost.Dto.categoryDto;
import com.instagram.post.instapost.Dto.categoryDtoResp;
import com.instagram.post.instapost.Entity.CategoryEntity;
import com.instagram.post.instapost.Repository.categoryRepo;
import com.instagram.post.instapost.Services.category.categoryServices;

@Service
public class CateServ implements categoryServices {
    private categoryRepo categoryRepository;
    public CateServ(categoryRepo categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public categoryDto createCategory(String categoryName) {
        // Logic to create a category
        // convert the category string in to lowercase
        String catName=categoryName.trim().toLowerCase().replaceAll("^\"|\"$", "");
        // we use repace because the while return the result it shows the result with extra "/" in the start and end of the string so that is why we use it.

        // first check if category name is already exists 
        boolean exist=categoryRepository.existsByCategoryName(catName);
        System.out.println("exist: " + exist);
        if(exist){
            return null;
        }

        CategoryEntity entity=new CategoryEntity();
        entity.setCategoryName(catName);

        // add the the category instance in the database
        CategoryEntity savedEntity = categoryRepository.save(entity);

        // convert the saved entity to categoryDto and return it
        return new categoryDto(savedEntity.getId(),savedEntity.getCategoryName());
    }

    public categoryDto getCategoryById(Long id) {
        // Logic to get a category by ID
        return null;
    }

    public categoryDto updateCategory(Long id, categoryDto categoryDto) {
        // Logic to update a category
        return null;
    }

    public categoryDtoResp getallInformationAboutCategory(Long id) {
        // Logic to get all information about a category
        return null;
    }
}
