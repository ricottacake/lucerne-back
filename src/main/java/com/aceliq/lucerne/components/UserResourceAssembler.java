package com.aceliq.lucerne.components;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import com.aceliq.lucerne.controller.WebController;
import com.aceliq.lucerne.model.User;

public class UserResourceAssembler extends RepresentationModelAssemblerSupport<User, UserResource> {

  public UserResourceAssembler() {
    super(WebController.class, UserResource.class);
  }

  @Override
  protected UserResource instantiateModel(User user) {
    return new UserResource(user);
  }

  @Override
  public UserResource toModel(User entity) {
    UserResource v = createModelWithId(entity.getId(), entity);
    return v;
  }
}
