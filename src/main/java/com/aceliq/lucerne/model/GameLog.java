package com.aceliq.lucerne.model;

public class GameLog {

  private String publicId;
  private String name;
  private String type;

  public String getPublicId() {
    return publicId;
  }

  public void setPublicId(String publicId) {
    this.publicId = publicId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public GameLog(String publicId, String name, String type) {
    super();
    this.publicId = publicId;
    this.name = name;
    this.type = type;
  }
}
