package com.instagram.post.instapost.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.instagram.post.instapost.Entity.UserEntity;
import com.instagram.post.instapost.Repository.userRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private userRepo userRepository;

    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     UserEntity user = userRepository.findByUsername(username);
    //     if (user == null) {
    //         throw new UsernameNotFoundException("User Not Found with username: " + username);
    //     }
    //     return new org.springframework.security.core.userdetails.User(
    //         user.getUsername(),
    //         user.getPassword(),
    //         Collections.emptyList()
    //     );
    // }

    public UserDetails loadUserByUserId(long id) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        
        // return new org.springframework.security.core.userdetails.User(
        //         user.getUsername(),
        //         user.getPassword(),
        //         Collections.emptyList()
        // );
        return user;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Looking up user: " + email);
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        // return new org.springframework.security.core.userdetails.User(
        //     user.getEmail(),
        //     user.getPassword(),
        //     Collections.emptyList()
        // );

        return user;
    
    }
}
