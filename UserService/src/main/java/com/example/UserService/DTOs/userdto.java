package com.example.UserService.DTOs;

import java.util.List;

import com.example.UserService.Models.Post;
import com.example.UserService.Models.User;
import com.example.UserService.Models.friends;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class userdto {
    
private User user;
private List<Post> p;
private List<friends> followers;
private String Token;


}
