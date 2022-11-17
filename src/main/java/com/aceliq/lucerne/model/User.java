package com.aceliq.lucerne.model;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

@Entity
@Table(name = "Users")
public class User {

  @Id
  @JsonView(Views.Detailed.class)
  @JsonIgnore
  private String id;

  @Column(name = "public_id")
  private String publicId;

  private String username;

  private String country;

  @JsonIgnore
  private String datereg;

  public User() {
    this.id = Hashing.sha256()
        .hashString(
            String.valueOf(
                new Timestamp(System.currentTimeMillis() * new Random().nextInt()).getTime()),
            StandardCharsets.UTF_8)
        .toString();
    this.datereg = String.valueOf(System.currentTimeMillis() / 1000L);
    this.publicId = Hashing.md5().hashString(this.id, Charsets.UTF_8).toString();
  }

  public void setName(String name) {
    this.username = name;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getName() {
    return username;
  }

  public String getCountry() {
    return country;
  }

  public String getPublicId() {
    return publicId;
  }

  @JsonIgnore
  public String getDate() {
    return datereg;
  }

  public String getId() {
    return id;
  }
}
