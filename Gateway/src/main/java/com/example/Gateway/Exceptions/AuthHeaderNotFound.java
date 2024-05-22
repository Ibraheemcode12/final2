package com.example.Gateway.Exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthHeaderNotFound extends Exception {
    
public AuthHeaderNotFound(String msg){
super(msg);
}

}
