package com.example.PostServicee.Feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.PostServicee.DTO.like_comeents_dto;
import com.example.PostServicee.Models.deleteobj;

@FeignClient("LIKCOMMENTCONFIG")
public interface like_comment_feign {
    
   @GetMapping("/com_like/{postId}")
    public ResponseEntity<like_comeents_dto> getAllComments_likes(@PathVariable long postId);
    
       @DeleteMapping("/com_like/{postId}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long postId);

       @DeleteMapping("/com_like/All")
    public ResponseEntity<Boolean> deletePosts(@RequestBody deleteobj delete);
  
    
}
