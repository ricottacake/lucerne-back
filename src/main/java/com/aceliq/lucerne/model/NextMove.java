package com.aceliq.lucerne.model;

public class NextMove {

  private String playerId;
  private String name;

  public NextMove(String playerId, String name) {
    super();
    this.playerId = playerId;
    this.name = name;
  }

  public String getPlayerId() {
    return playerId;
  }

  public String getName() {
    return name;
  }
}
