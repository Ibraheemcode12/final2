package com.example.like_comment_service.Services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.like_comment_service.DAOs.commentrepo;
import com.example.like_comment_service.DAOs.comments_likes;
import com.example.like_comment_service.DAOs.likerepo;
import com.example.like_comment_service.DTO.like_comeents_dto;
import com.example.like_comment_service.Exceptions.CommentNotFoundException;
import com.example.like_comment_service.Exceptions.UnauthorizedException;
import com.example.like_comment_service.Models.Comments;
import com.example.like_comment_service.Models.Like;
import com.example.like_comment_service.Models.commentlike;

import jakarta.transaction.Transactional;


@Service
public class comment_like_service {

    Logger logger = LoggerFactory.getLogger(comment_like_service.class);

    @Autowired
    commentrepo commentrepo;

    @Autowired
    likerepo likerepo;

    @Autowired
    comments_likes commentslikes;

// NOTE : THESE RANDOM CHARECHTERS YOU SEE LIKE \u001B[31 m IN THE LOOGER ARE JUST USED TO CHANGE THE STRING COLOR IN THE CONSOLE WHICH MAKES IT EASIER TO NOTICE THE ERROR or WARNING
// and differentiate between the two.



            
    public like_comeents_dto get_likes_comments(Long id) { // retrieveing likes and comments of a specific post. 

        try {
            List<Comments> list = commentrepo.findAllByPostid(id);
            List<Like> list2 = likerepo.findAllByObjid(id);



            if (list.isEmpty()) { 
                logger.warn("\u001B[33m The following post with id " + id + " does not have any comments \u001B[0m");
            }

            if (list2.isEmpty()) {
                logger.warn("\u001B[33m The following post with id " + id + " does not have any Likes \u001B[0m");
            }

            return new like_comeents_dto(list2, list); // returning a Data transfer object (DTO)  contaning the requested likes and comments of a post

        } catch (Exception error) {
            logger.error("\u001B[31m " + error + "\u001B[0m");
            return null;
        }

    }

@Transactional
 public Integer save_like_for_posts(Like like,String username){ //  Adding a like to a post

    try{ 

        if(like.getAuthorName() != null){ // Here we check if the sorce that sent the like did not put an author name and throwing an exception,
                                          // because it is the apps job to sign the likes authorname using jwt.
            throw new UnauthorizedException("You are not allowed to add a like");
        }


        if(likerepo.findByObjidAndAuthorName(like.getObjid(), username) != null){ // checking if the user has already liked this post or comment and removing it if so.
            likerepo.deleteByObjidAndAuthorName(like.getObjid(), username);
            return likerepo.findAllByObjid(like.getObjid()).size();
        }

         

like.setAuthorName(username); // signing the authorname using username extracted from the token and sent thorugh a header.
likerepo.save(like);
return likerepo.findAllByObjid(like.getObjid()).size();
    }catch(Exception error){
        logger.error("\u001B[31m " + error + "\u001B[0m");
        return 0;
    }

 }


 @Transactional
 public Integer save_like_for_comments(commentlike like,String username){ //  Adding a like to a post

    try{ 

        if(like.getAuthorName() != null){ // Here we check if the sorce that sent the like did not put an author name and throwing an exception,
                                          // because it is the apps job to sign the likes authorname using jwt.
            throw new UnauthorizedException("You are not allowed to add a like");
        }

        Comments comment = commentrepo.findById(like.getObjid()).get();

        if(commentslikes.findByObjidAndAuthorName(like.getObjid(), username) != null){ // checking if the user has already liked this post or comment and removing it if so.
            commentslikes.deleteByObjidAndAuthorName(like.getObjid(), username);
            comment.setLikes(comment.getLikes()-1);
         commentrepo.save(comment);
            return comment.getLikes();
        }

                  

like.setAuthorName(username); // signing the authorname using username extracted from the token and sent thorugh a header.
commentslikes.save(like);
comment.setLikes(comment.getLikes()+1);
commentrepo.save(comment);
return comment.getLikes();
    }catch(Exception error){
        logger.error("\u001B[31m " + error + "\u001B[0m");
        return 0;
    }

 }


 public boolean save_comment(Comments comments,String username){

     // Same thing as like case here but for the comments.
    try{
        if(comments.getUserName() != null){
            throw new UnauthorizedException("You are not allowed to add a comment");
        }

comments.setUserName(username);
  commentrepo.save(comments);
  
        return true;
    }catch(Exception error){
        logger.error("\u001B[31m " + error + "\u001B[0m");
        return false;
    }

 }

@Transactional
 public boolean Delete_likes_comments(Long id){ // This method deletes all likes and comments for ONE post. Each like and comment have a postid that connects them
                                                // to a specific post.
try{
 
commentrepo.deleteAllByPostid(id);
likerepo.deleteAllByObjid(id);
    return true;
}catch(Exception error){
    logger.error("\u001B[31m " + error + "\u001B[0m");
return false;
} 
}


@Transactional
public boolean Delete_likes_comments_for_posts(List<Long> list,String username) { // This method deletes comments and likes for MORE THAN ONE POST.
try{

commentrepo.deleteAllByPostidIn(list); // passing a list of post ids
likerepo.deleteAllByObjidIn(list);
commentrepo.deleteByuserName(username);
likerepo.deleteByAuthorName(username);
   return true;
}catch(Exception error){
   logger.error("\u001B[31m " + error + "\u001B[0m");
return false;
} 
}

@Transactional
public boolean Delete_comment(Long id,String username){ // this method deletes a comment
    try{

       Optional<Comments> com = commentrepo.findById(id);
        if(com.isEmpty()){
             throw new CommentNotFoundException("Comment not found."); // throwing an exception to indicate that an error has occured because normally we should find a comment
        }

       if(!com.get().getUserName().equals(username)){ // Checking if the username sent through the api-gate-way matches the author of this comment.
        throw new UnauthorizedException("You are not allowed to delete this comment");
       }

       commentslikes.deleteAllByObjid(id);
       commentrepo.deleteById(id);
           return true;
        }catch(Exception error){
           logger.error("\u001B[31m " + error + "\u001B[0m");
        return false;
        } 


}

public Comments update_Comment(Comments comments,String username){ // Same logic as the above method but we are updating a comment here.

    try{

        Optional<Comments> com = commentrepo.findById(comments.getId());
        if(com.isEmpty()){
            throw new CommentNotFoundException("Comment not found.");
        }

       if(!com.get().getUserName().equals(username)){
        throw new UnauthorizedException("You are not allowed to delete this comment");
       }

      com.get().setContent(comments.getContent());  
          commentrepo.save(com.get());
          return com.get();
         }catch(Exception error){
            logger.error("\u001B[31m " + error + "\u001B[0m");
         return null;
         } 

}


}