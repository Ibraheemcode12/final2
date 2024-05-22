package com.example.PostServicee.DTO;

import java.util.List;

import com.example.PostServicee.Models.Comments;
import com.example.PostServicee.Models.Like;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class like_comeents_dto {
    
private List<Like> likes;
private List<Comments> comments;

}
