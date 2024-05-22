package com.example.PostServicee.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PostServicee.Models.Post;
import java.util.*;

public interface Postrepo extends  JpaRepository<Post, Long>{

 
   List<Post> findAllByauthorName(String authorName);

   Optional<Post> findByAuthorNameAndId(String username,Long id);
    void deleteAllByauthorName(String username);
    void deleteByAuthorNameAndId(String author,Long id);
}
