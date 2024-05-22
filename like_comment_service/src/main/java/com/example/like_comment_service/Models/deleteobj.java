package com.example.like_comment_service.Models;

import java.util.List;

import lombok.Data;

@Data
public class deleteobj {
    
    private List<Long> deletelist;
    private String username;

}
