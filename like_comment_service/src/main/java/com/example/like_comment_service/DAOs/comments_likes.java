package com.example.like_comment_service.DAOs;


import com.example.like_comment_service.Models.Like;
import com.example.like_comment_service.Models.commentlike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface comments_likes extends JpaRepository<commentlike,Long>{
    
  public List<Like> findAllByObjid(Long postid);
    public Like findByObjid(Long postid);


    public void deleteAllByObjidIn(List<Long> postid);

    public void deleteAllByObjid(Long postid);

    public void deleteByAuthorName(String AuthorName);

    public void deleteByObjidAndAuthorName(Long objid,String author);

    public commentlike findByObjidAndAuthorName(Long objid,String author);

}
