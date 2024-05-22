package com.example.UserService.Exceptions;

public class UserExistsException extends Exception{

    public UserExistsException(String errmsg){
        super(errmsg);
    }
    
}
