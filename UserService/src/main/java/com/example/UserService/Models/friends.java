package com.example.UserService.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class friends {

private String follower;
private String following;
@Id @GeneratedValue Long id;


}

