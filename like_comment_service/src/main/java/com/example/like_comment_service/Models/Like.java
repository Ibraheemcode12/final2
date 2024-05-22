package com.example.like_comment_service.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "likes")
public class Like {
    
@Id @GeneratedValue private Long id;

private String authorName;
private Long postid;


public Like(String auth,Long ID){
    this.authorName=auth;
    this.postid=ID;
}

public Like(){

}

}
