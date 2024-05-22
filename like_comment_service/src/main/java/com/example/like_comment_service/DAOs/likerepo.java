package com.example.like_comment_service.DAOs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.like_comment_service.Models.Like;


public interface likerepo extends JpaRepository<Like,Long> {

    public List<Like> findAllByPostid(Long postid);
    public Like findByPostid(Long postid);


    public void deleteAllByPostidIn(List<Long> postid);

    public void deleteAllByPostid(Long postid);

    public void deleteByAuthorName(String AuthorName);
}
