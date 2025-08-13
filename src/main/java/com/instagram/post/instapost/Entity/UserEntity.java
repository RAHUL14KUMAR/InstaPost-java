package com.instagram.post.instapost.Entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="usersTable")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String username;

    @Email
    @Column(nullable=false,unique=true)
    private String email;

    @ToString.Exclude
    @Column(nullable=false)
    private String Password;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name="user_category",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="category_id"))
    private Set<CategoryEntity> userInterestInCategory;

    @ToString.Exclude
    @OneToMany(mappedBy="user",cascade = CascadeType.ALL)
    private List<PostEntity> posts;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.Password;
    }

    @Override
    public String getUsername() {
        return this.email; // or username depending on your auth logic
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
