package com.example.UserService.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.UserService.UserAssembler;
import com.example.UserService.DTOs.userdto;
import com.example.UserService.Models.User;
import com.example.UserService.Models.friends;
import com.example.UserService.Service.DataService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping(value = "user")
public class UserController {

  UserAssembler userAssembler;
  
  @Autowired
  DataService dataService;


  @GetMapping("/username/{user}")
  public ResponseEntity<userdto> getUser(@PathVariable String user) {
    return new ResponseEntity<userdto>(dataService.get_user(user),HttpStatus.OK);
  }



  @PostMapping("/login")
  public ResponseEntity<userdto> Login(@RequestBody User user) {
return dataService.Look_for_user_upon_sign_in(user);
  }



  @PostMapping(value = "/sign-up")
  public ResponseEntity<User> sign_up(@RequestBody User user) {
   return new ResponseEntity<User>(dataService.CreatUser(user),HttpStatus.OK);
  }

 
   @PostMapping("/follow")
   public ResponseEntity<Boolean> addfreind(@RequestBody friends entity,@RequestHeader("Username") String username) {
       return ResponseEntity.ok(dataService.follow(entity,username));
   }
   


   @DeleteMapping("/username")
   public boolean deleteUser(@RequestHeader("Username") String username2){
       return dataService.deleteUser(username2);
   }

   @DeleteMapping("/unfollow/{following}")
   public ResponseEntity<Boolean> unfollow(@PathVariable String following,@RequestHeader("Username") String follower){
    return ResponseEntity.ok(dataService.unfollow(following,follower));
   }


   @PutMapping("/username")
   public ResponseEntity<User> putMethodName(@RequestHeader("Username") String username, @RequestBody User entity) {
       return ResponseEntity.ok(dataService.edit_user(username, entity));
   }

   ////
}
