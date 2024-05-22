package com.example.PostServicee.Exceptions;

public class PostNotFoundException extends Exception{
    
public PostNotFoundException(String errmsg){
super(errmsg);
}

public PostNotFoundException(){
    super();
}

}
