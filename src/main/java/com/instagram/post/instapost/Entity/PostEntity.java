package com.instagram.post.instapost.Entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="postsTable")
public class PostEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String postDesc;

    @ManyToMany
    @JoinTable(name="post_category",joinColumns=@JoinColumn(name="post_id"),inverseJoinColumns=@JoinColumn(name="category_id"))
    private Set<CategoryEntity> postCategory;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
}
