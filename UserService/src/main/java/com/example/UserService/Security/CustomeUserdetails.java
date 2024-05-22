package com.example.UserService.Security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.UserService.Models.User;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomeUserdetails implements UserDetails{

private String username;
private String password;



    public CustomeUserdetails(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
    }

    @Override
    public String getPassword() {
    return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
    return true;
    }

    @Override
    public boolean isAccountNonLocked() {
       return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
     return true;
    }
    
}
