package com.example.Gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class controller {
    
@RequestMapping("/fallback")
public Mono<String> fallback() {
  return Mono.just("Something went wrong...");
}

}
