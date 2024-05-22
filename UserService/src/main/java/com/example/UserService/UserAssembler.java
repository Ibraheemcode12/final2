package com.example.UserService;


// import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
// import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.UserService.Models.User;

@Component
public class UserAssembler implements RepresentationModelAssembler<User,EntityModel<User>>{

    @Override
    public EntityModel<User> toModel(User entity) {
    //    return EntityModel.of(entity, 
    //    linkTo(methodOn(PostController.class).all(entity.getId())).withRel("Posts")     
    //    );        

return null;
    }
    

}
