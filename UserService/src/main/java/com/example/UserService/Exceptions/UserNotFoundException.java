package com.example.UserService.Exceptions;

public class UserNotFoundException extends Exception {
    
    public UserNotFoundException(String errmsg){
        super(errmsg);
    }


}
