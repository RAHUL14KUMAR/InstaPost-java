package com.instagram.post.instapost.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.post.instapost.Dto.ResponseDto;
import com.instagram.post.instapost.Dto.categoryDto;
import com.instagram.post.instapost.Exeption.CategoryException;
import com.instagram.post.instapost.Services.category.categoryServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
public class CategoryController {
    private categoryServices catServ;
    public CategoryController(categoryServices catServ) {
        this.catServ = catServ;
    }

    // add category in the database
    @PostMapping("/create-category")
    public ResponseEntity<ResponseDto> addCatInDb(@RequestBody String catName, HttpServletRequest req, HttpServletResponse res) {
        try {
            categoryDto result=catServ.createCategory(catName);
            if(result==null){
                throw new CategoryException("Category already exists");
            }
            ResponseDto resp=new ResponseDto();
            resp.setAnswer(result);
            resp.setMessage("Category created successfully");
            return ResponseEntity.status(200).body(resp);
        } catch (Exception e) {
            ResponseDto resp=new ResponseDto();
            resp.setAnswer(null);
            resp.setMessage(e.getMessage());
            return ResponseEntity.status(403).body(resp);
        }
    }
}