package com.example.like_comment_service.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "commentlikes")
public class commentlike {
    
@Id @GeneratedValue private Long id;

private String authorName;
private Long objid;


public commentlike(String auth,Long ID){
    this.authorName=auth;
    this.objid=ID;
}

public commentlike(){

}

}
