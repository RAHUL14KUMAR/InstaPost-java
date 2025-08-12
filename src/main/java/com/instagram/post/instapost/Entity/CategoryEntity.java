package com.instagram.post.instapost.Entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = {"posts", "users"})
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @ManyToMany(mappedBy="postCategory")
    private List<PostEntity> posts;

    @ManyToMany(mappedBy="userInterestInCategory")
    private Set<UserEntity> users;
}
