package com.example.like_comment_service.DAOs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.like_comment_service.Models.Comments;



public interface commentrepo extends JpaRepository<Comments,Long>{
    
 public List<Comments> findAllByPostid(Long id);

  public void deleteAllByPostidIn(List<Long> postid);

    public Comments findByPostid(Long postid);

  public void deleteAllByPostid(Long postid);

  public void deleteByuserName(String user);

}
