package com.aceliq.lucerne.components;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class OnlinePlayers {

  Map<String, String> players;

  public Map<String, String> getPlayers() {
    return players;
  }

  public void setPlayers(Map<String, String> players) {
    this.players = players;
  }
}
