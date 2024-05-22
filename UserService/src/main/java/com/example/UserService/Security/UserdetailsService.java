package com.example.UserService.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.UserService.Dao.UserRepo;
import com.example.UserService.Models.User;


@Component
public class UserdetailsService implements UserDetailsService{

@Autowired
UserRepo userRepo; 


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   User user =  userRepo.findByUsername(username);   
    
if(user == null){
    throw new UsernameNotFoundException("Username or password wrong...");
}

   return new CustomeUserdetails(user);
    }
    

}
