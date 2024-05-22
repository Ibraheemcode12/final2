package com.example.UserService.Models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Like {
    
@Id @GeneratedValue private Long id;

private String AuthorName;
private Long postid;

public Like(String auth,Long ID){
    this.AuthorName=auth;
    this.postid=ID;
}

public Like(){

}

}
