package com.example.UserService.Dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserService.Models.User;




public interface UserRepo extends JpaRepository<User, Long> {
    
   public User findByUsernameAndPassword(String name,String password);

public User findByUsername(String username);
void deleteByUsername(String Following);

}
