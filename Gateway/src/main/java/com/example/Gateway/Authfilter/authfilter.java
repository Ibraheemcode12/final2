package com.example.Gateway.Authfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import com.example.Gateway.Exceptions.AuthHeaderNotFound;
import com.google.common.net.HttpHeaders;

@Component
public class authfilter extends AbstractGatewayFilterFactory<authfilter.Config> {

    @Autowired
    RoutrValidator routrValidator;

    @Autowired
    Jwtservice jwtservice;

    public authfilter() {
        super(Config.class);
    }

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(authfilter.class);

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {

            try {
                if (!routrValidator.AllowWithoutToken.test(exchange.getRequest())) {
                    return chain.filter(exchange);
                }

        

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new AuthHeaderNotFound("Missing Autherization Header.");
                }


                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if(authHeader == null || !authHeader.startsWith("Bearer ")){
                    throw new AuthHeaderNotFound("Autherization Header is Empty or Does not contain Bearer token");
                }

                
                 

String token = authHeader.substring(7);


                jwtservice.validateToken(token);

                //  if(jwtservice.isTokenExpired(token)){
                //     throw new TokenExpiredException("Token Expired.");
                //  }
               
                String username = jwtservice.extractUsername(token);
             
                 ServerHttpRequest req = exchange.getRequest().mutate()
                 .header("Username", username).build();


                return chain.filter(exchange.mutate().request(req).build());
            } catch (Exception error) {
                logger.error("\u001B[31m " + error + " \u001B[0m");
                return null;
            }
        });
    }

    public static class Config {

    }

}
