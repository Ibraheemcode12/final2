package com.example.like_comment_service.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.like_comment_service.DTO.like_comeents_dto;
import com.example.like_comment_service.Models.*;
import com.example.like_comment_service.Services.comment_like_service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;






@RestController
@RequestMapping(value = "com_like")
public class Comments_likes_Controller {

   @Autowired
   comment_like_service commentservice;
   
   @GetMapping("/{postId}")
   public ResponseEntity<like_comeents_dto> getAllComments_likes(@PathVariable long postId) throws InterruptedException {
       // Thread.sleep(10000);
           return ResponseEntity.ok(commentservice.get_likes_comments(postId));        
   }



   @PostMapping("/like")
   public ResponseEntity<Integer> create_like_for_posts(@RequestBody Like  like,@RequestHeader("Username") String username) {  // the Json obj sent from the user will contain his/her username and the postid they liked
       return ResponseEntity.ok(commentservice.save_like_for_posts(like,username));
   }


   
   @PostMapping("/like_comment")
   public ResponseEntity<Integer> create_like_for_comment(@RequestBody commentlike  like,@RequestHeader("Username") String username) {  // the Json obj sent from the user will contain his/her username and the postid they liked
       return ResponseEntity.ok(commentservice.save_like_for_comments(like,username));
   }


   @PostMapping("/comment")
   public ResponseEntity<Boolean> create_comment(@RequestBody Comments  comment,@RequestHeader("Username") String username) { //the json obj sent will contain the postid.
       return ResponseEntity.ok(commentservice.save_comment(comment,username));
   }
   
  
@PutMapping("/comment")
public ResponseEntity<Comments> putMethodName(@RequestBody Comments entity,@RequestHeader("Username") String username) {
    
    return ResponseEntity.ok(commentservice.update_Comment(entity,username));
}


@DeleteMapping("/All")
public ResponseEntity<Boolean> deletePosts_likes_comments(@RequestBody deleteobj delete) {
    return ResponseEntity.ok(commentservice.Delete_likes_comments_for_posts(delete.getDeletelist(),delete.getUsername()));
}



      @DeleteMapping("/{postId}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentservice.Delete_likes_comments(postId));
    }
  
// the difference between these two is one is made to delete one post and one to delte all posts for one user


    @DeleteMapping("/comment/{deleteid}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable Long deleteid,@RequestHeader("Username") String username) {
        return ResponseEntity.ok(commentservice.Delete_comment(deleteid,username));
    }
    

}
