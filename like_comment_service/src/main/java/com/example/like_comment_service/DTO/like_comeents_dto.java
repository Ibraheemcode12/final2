package com.example.like_comment_service.DTO;

import java.util.List;

import com.example.like_comment_service.Models.Comments;
import com.example.like_comment_service.Models.Like;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class like_comeents_dto {
    
private List<Like> likes;
private List<Comments> comments;

}
