package com.example.PostServicee.Models;

import java.util.List;

import lombok.Data;

@Data
public class deleteobj {
    private List<Long> deletelist;
    private String username;

    public deleteobj(List<Long> list,String user){
        this.deletelist=list;
        this.username=user;
    }

}
