package com.example.Gateway.Authfilter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


@Component
public class RoutrValidator {
    
public static final List<String> Endpoints = List.of(
"/user/login","/user/sign-up"
);


public Predicate<ServerHttpRequest> AllowWithoutToken = req -> Endpoints.stream()
.noneMatch(uri -> req.getURI().getPath().contains(uri));





}
