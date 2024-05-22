package com.example.PostServicee.Models;

import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Post {

 
    private @Id @GeneratedValue Long id;

    @NotBlank(message = "Type somethin...")
    private String content;
    
    private String path;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;

    public Post() {

    }
    
   private String authorName;

   public Post( String content, String username) {
    this.content = content;
    this.timestamp = LocalDateTime.now(); 
  }



}
