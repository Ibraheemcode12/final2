package com.example.PostServicee.Service;


import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.PostServicee.DAO.Postrepo;
import com.example.PostServicee.DTO.like_comeents_dto;
import com.example.PostServicee.DTO.postdto;
import com.example.PostServicee.Exceptions.NotAuthorizedException;
import com.example.PostServicee.Exceptions.PostNotFoundException;
import com.example.PostServicee.Feign.like_comment_feign;
import com.example.PostServicee.Models.Post;
import com.example.PostServicee.Models.deleteobj;
import jakarta.transaction.Transactional;


@Service
public class PostService {

// NOTE : THESE RANDOM CHARECHTERS YOU SEE LIKE \u001B[31 m ARE JUST USED TO CHANGE THE STRING COLOR IN THE CONSOLE WHICH MAKES IT EASIER TO NOTICE THE ERROR or WARNING
// and differentiate between the two.

//NOTE : THE USERNAME PARMETER IN EACH METHOD IS SENT THROUGH A USERNAME HEADER BY THE API GATEWAY, THE API GATEWAY GETS THE REQUEST FIRST THEN CHECKS
// FOR THE TOKEN HEADER, VALIDATES THE TOKEN THEN IT GETS THE USERNAME FROM IT, THIS WAY NO ONE WILL BE ABLE TO DO SOMETHING 
// TO A POST EXCEPT THE POST OWNER.


    @Autowired
    Postrepo postrepo;

    @Autowired
    like_comment_feign like_comment_feign; // Connecting to the like comments service

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PostService.class);

    private final String folder = "C:/Users/Bashoofak/Desktop/final1/PostServicee/src/main/java/com/example/PostServicee/images/";


    // Wont be calling the comment_like microservice for the get_posts because this will the slow systme a lot, in short Having to call the comment_like service like a 1000 or more times to load 
    // one user does not sound lika a good idea right? We are talking about best senario here what if other users have 2000+ posts?
    // so instead on the front end side we will let the user chose what post he wants and the system will load the comments and likes of  that one.
    public List<Post> get_posts(String username) { 
        List<Post> posts = postrepo.findAllByauthorName(username);

        try{
            
        if (posts.isEmpty()) {
            logger.warn("\u001B[33m User " + username + " does not have any posts \u001B[0m"); 
            return null;                                                                                                                                                                                                                         
        }
        return posts;
    }catch(Exception error){
logger.error("\u001B[31m "+error+" \u001B[0m");
return null;
    }


    }


    //Loading Posts of other users to display them in the main app page
    public List<Post> get_Random_posts() {

        try{
        List<Post> posts = postrepo.findAll(); // quering all posts
        return posts;
        }catch(Exception error){
            logger.error("\u001B[31m "+error+" \u001B[0m");
            return null;
        }
    }

 // Loading one post with all of its comments and likes
    public postdto get_one_post(Long id) {
        Optional<Post> post = postrepo.findById(id);

        try{

        if (post.isEmpty()) {
            logger.warn("\u001B[33m  NO POST WITH THE ID " + id + " WAS FOUND \u001B[0m"); // Checking if the post exists and throwing an exception if it does not.
            throw new PostNotFoundException("No post with id "+id);
        }

        like_comeents_dto dto = like_comment_feign.getAllComments_likes(id).getBody(); // Using feign to get the comments and likes of the post after checking that it exists

      if(dto.getComments().isEmpty()){
        logger.info("\u001B[33m  POST WITH THE ID " + id + " does not have any comments yet. \u001B[0m"); // Setting a warning for debugging purposes
      }


        return new postdto(post.get(),dto.getLikes(),dto.getComments()); // Returning a post DTO (Data transfer Object)
                                                                         // that contains the requested post along with its
                                                                         // Comments and likes    
    }
catch(Exception error){
    logger.error("\u001B[31m "+error+" \u001B[0m");
    return null;
}

    }


public ResponseEntity<?> getimage(String path){
    try{
    byte [] img = Files.readAllBytes(new File(folder+path+".png").toPath());
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(img);
    }catch(Exception e){
        return null;
    }
}

  
    public boolean Add_post(String content,String username,MultipartFile file){
        try{
            Post post = new Post();
            String uuid = UUID.randomUUID().toString();
            String path = folder+uuid+".png";
            file.transferTo(new File(path));
            post.setTimestamp(LocalDateTime.now()); // marking the post creation time
            post.setAuthorName(username); // setting the author name
            post.setPath(uuid); // Saving the post image path.
            post.setContent(content);
            postrepo.save(post); // Saving it into the database
            return true;
        }
catch(Exception error){
logger.error("\u001B[31m "+error+" \u001B[0m");
return false;
}

    }


//Deleting a post along side with the comments and likes related to it.
@Transactional
  public boolean Delete_post(Long id,String username){

    try{

Optional<Post> op = postrepo.findById(id);

if(op.isEmpty()){ // Checking if the post exists and throwing an exception if it does not. 
    throw new PostNotFoundException("Post does not exist");
}

if(!op.get().getAuthorName().equals(username)){ // Authorizing the request by checking if the authorname of the post matches the username sent through the header, the
                                                // username value in the header was given by the api gate way after it has validated the token and extracted the username after.

    throw new NotAuthorizedException("You aren not allowed to delete this post.");
}

        postrepo.deleteByAuthorNameAndId(username,id); 
        like_comment_feign.deletePost(id); // deleting likes and comments of this post by sending a deletetion req to the like_comment service.
        return true;
    }catch(Exception error){
        logger.error("\u001B[31m "+error+" \u001B[0m");
        return false;
    }

  }


 public Post update_post(Post upost,String username){
Optional<Post> post = postrepo.findByAuthorNameAndId(username,upost.getId());

try{

    if (post.isEmpty()) {  // Checking if the post exists and throwing and exception if it does not, IF a post does not exist this means the api was approached by a 3rd party.
        throw new PostNotFoundException("Unauthorized Accsess to post "+upost.getId());
    }

    Post p = post.get();

p.setContent(upost.getContent());

postrepo.save(p);

    return p;
}catch(Exception error){
    logger.error("\u001B[31m "+error+" \u001B[0m");
return null;
}

 }


@Transactional
 public boolean Delete_posts(String username){ // this main thing this method does is it deletes all the posts for a user when he/she delete their account

    try{

List<Long> list = postrepo.findAllByauthorName(username).stream().map(p -> p.getId()).toList();

        like_comment_feign.deletePosts(new deleteobj(list,username)); // Sending a list of post ids to the comment_like service to delete all the likes and comments reltaed to
                                                            // the users posts.
                                                            
        postrepo.deleteAllByauthorName(username); // deleting all the posts reltaed to a specific user.
        return true;
    }catch(Exception error){
        logger.error("\u001B[31m "+error+" \u001B[0m");
        return false;
    }

  }




}
