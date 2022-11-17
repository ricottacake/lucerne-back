package com.aceliq.lucerne.components;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import com.aceliq.lucerne.model.User;

@Relation(value = "tacsssafds232o", collectionRelation = "tacoss")
public class UserResource extends RepresentationModel<UserResource> {

  private String username;
  private String datereg;
  private String country;

  public UserResource(User user) {

    this.username = user.getName();
    this.country = user.getCountry();
    this.datereg = user.getDate();
  }
}
