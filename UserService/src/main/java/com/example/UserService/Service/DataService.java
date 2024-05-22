package com.example.UserService.Service;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.UserService.DTOs.userdto;
import com.example.UserService.Dao.UserRepo;
import com.example.UserService.Dao.freindsrepo;
import com.example.UserService.Exceptions.UserExistsException;
import com.example.UserService.Exceptions.UserNotFoundException;
import com.example.UserService.Models.Post;
import com.example.UserService.Models.User;
import com.example.UserService.Models.friends;
import com.example.UserService.Security.Jwtservice;
import com.example.UserService.Userfeign.Userfiegn;


import jakarta.transaction.Transactional;

@Service
public class DataService {

    @Autowired
    UserRepo userrepo;

    @Autowired
    freindsrepo freindsrepo;

    @Autowired
    Userfiegn postfeign;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Jwtservice jwtservice;

    @Autowired
    AuthenticationManager authenticationManager;

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DataService.class);

    // this method retrieves a user with the users followed and his posts but does not retrieve the posts with there comments and likes for performence purposes ,
    // the user choses a post and the post service will load the comments and likes of that post.
    public userdto get_user(String username) {

        try {
            User user = userrepo.findByUsername(username);
            List<Post> list = postfeign.all(username).getBody(); // getting the posts from potsservice using feign 
            List<friends> friends = freindsrepo.findAllByFollowing(username); // getting the list of users that are followed by this account
            return new userdto(user, list, friends, null);

        } catch (Exception error) {
            logger.error("\u001B[31m " + error + " \u001B[0m");
            return null;
        }

    }

    // Signing up a user
    public User CreatUser(User user) {
        try {

            if (userrepo.findByUsername(user.getUsername()) != null) { // Checking if the username exists.
                throw new UserExistsException("User already exists...");
            }

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // Encrypting the username password before storing it in the DB.

            return userrepo.save(user);
        } catch (Exception error) {
            logger.error("\u001B[31m " + error + " \u001B[0m");
            return null;
        }
    }

    // sign in method
    public ResponseEntity<?> Look_for_user_upon_sign_in(User userreq) {
        try {
        
            // giving the user req password and username to the auth manager to check if they are valid.
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userreq.getUsername(), userreq.getPassword()));

            if (!authentication.isAuthenticated()) { // throwing an exception if the credentials are not valid.
                throw new UserNotFoundException("Username or Password Wrong");
            }


            List<friends> friends = freindsrepo.findAllByFollowing(userreq.getUsername()); // loading the usernames this user follows.


            String token = jwtservice.GenerateToken(userreq.getUsername()); // Generating the jwt with the loged in username in it.

             
            return ResponseEntity.ok(new userdto(userreq, null, friends, token)); // sending a user Data transfer obj (DTO) as a response
        } catch (Exception error) {
            logger.error("\u001B[31m " + error + " \u001B[0m");
            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).build();
        }
    }

    // a method to send a follow req
    public boolean follow(friends friende,String username) { 
        try {

friende.setFollower(username);
            freindsrepo.save(friende);
            logger.info("User with username " + friende.getFollower() + " followed user with username " + friende.getFollowing());
            return true;
        } catch (Exception error) {
            logger.error("\u001B[31m " + error + " \u001B[0m");
            return false;
        }
    }

    @Transactional // Deleting a user 
    public boolean deleteUser(String username) {
        try {
            
            postfeign.deleteposts(username); // deleting all the posts of this user
            freindsrepo.deleteAllByFollower(username); // deleting all the connections related to this user, We are not deleting the users that follow or being followed
            freindsrepo.deleteAllByFollowing(username);//  we are just removing this user from their feed.
            userrepo.deleteByUsername(username); // deleting the user.
            logger.info("Deleted User " + username);
            return true;
        } catch (Exception error) {
            logger.error("\u001B[31m " + error + " \u001B[0m");
            return false;
        }

    }

    @Transactional
    // a method to unfollow a user.
    public boolean unfollow(String following,String follower) {
        try {
            freindsrepo.deleteByFollowingAndFollower(following, follower);
            logger.info("Unfollowed user with username " + following);
            return true;
        } catch (Exception error) {
            logger.error("\u001B[31m " + error + " \u001B[0m");
            return false;
        }

    }

}
