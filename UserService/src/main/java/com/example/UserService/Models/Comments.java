package com.example.UserService.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Comments {

    private  @Id @GeneratedValue Long commentId;
    private String userName;
    private long postid;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false) 
    private LocalDateTime date;

    @NotBlank(message = "type somethin....")
    private String content;


    
    public Comments(){}

    public Comments( String userName, String content) {
       
        this.userName = userName;
        this.content = content;
        this.date = LocalDateTime.now(); 
        
    }

   
}


