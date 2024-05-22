package com.example.UserService.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
public class User {
    
  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @NotBlank(message = "write your username")
    private String username;
    private String password;
    private String email;
    private String bio;


   


public User(){

}

public User(String username, String password) {
    this.username = username;
    this.password = password;
}



}
