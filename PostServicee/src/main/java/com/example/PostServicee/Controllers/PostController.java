package com.example.PostServicee.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.PostServicee.DTO.postdto;
import com.example.PostServicee.Models.Post;
import com.example.PostServicee.Service.PostService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@RequestMapping(value = "posts")
public class PostController {
  
    @Autowired
    private PostService postservice;



    @GetMapping("/All-posts/{username}")
    ResponseEntity<List<Post>> all(@PathVariable String username) throws InterruptedException {
        // Thread.sleep(10000);
        return ResponseEntity.ok(postservice.get_posts(username));
    }

 
    @GetMapping("/random")
    ResponseEntity<List<Post>> randomreq() {
        return ResponseEntity.ok(postservice.get_Random_posts());
    }


    @GetMapping("/one-post/{id}")
    public ResponseEntity<postdto> one(@PathVariable long id) {
            return ResponseEntity.ok(postservice.get_one_post(id));
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestParam String path) {
        return postservice.getimage(path);
    }
    

    @PostMapping("/post-for-user")
    public ResponseEntity<Boolean> Addpost(@RequestBody MultipartFile file,@RequestParam String content,@RequestHeader("Username") String username) {
        return ResponseEntity.ok(postservice.Add_post(content,username,file));
    }


    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long postId,@RequestHeader("Username") String username) {
        return ResponseEntity.ok(postservice.Delete_post(postId,username));
    }


    @DeleteMapping("/deltePosts")
    public ResponseEntity<Boolean> deleteposts(@RequestHeader("Username") String username){
        return ResponseEntity.ok(postservice.Delete_posts(username));
    }
  

    @PutMapping("/post")
    public ResponseEntity<Post> updatePost(@RequestBody Post updatedPost,@RequestHeader("Username") String username) {
            return ResponseEntity.ok(postservice.update_post(updatedPost,username)); 
    }


    
}
