package com.example.UserService.Userfeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.UserService.Models.Post;

@FeignClient("POSTSERVICECONFIG")
public interface Userfiegn {
    
    @GetMapping("/posts/All-posts/{username}")
    ResponseEntity<List<Post>> all(@PathVariable String username);

    
    @GetMapping("/posts/random")  // Reminder to go and create an api in post service that loads a couple of random requests (Current users Requests must not be included!!)
    public ResponseEntity<List<Post>> loadreq();


     @DeleteMapping("/posts/deltePosts")
    public ResponseEntity<Boolean> deleteposts(@RequestHeader("Username") String username);


}
