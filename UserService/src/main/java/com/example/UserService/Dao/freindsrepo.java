package com.example.UserService.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserService.Models.friends;

public interface freindsrepo extends JpaRepository<friends,Long>{
    
List<friends> findAllByFollowing (String following);
void deleteByFollowingAndFollower(String Following,String Follower);
void deleteAllByFollowing(String Following);
void deleteAllByFollower(String Follower);
friends findByFollowingAndFollower(String followoing,String Follower);

}
