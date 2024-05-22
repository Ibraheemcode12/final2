package com.example.PostServicee.DTO;

import java.util.List;

import com.example.PostServicee.Models.Comments;
import com.example.PostServicee.Models.Like;
import com.example.PostServicee.Models.Post;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class postdto {
    
private Post post;
private List<Like> Post_likes;
private List<Comments> Post_Comments;


public postdto(Post post){
    this.post=post;
}

}
