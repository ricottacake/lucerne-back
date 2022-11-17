package com.aceliq.lucerne.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.aceliq.lucerne.model.Game;

@Component
public class OnlineGames {

  private Map<Integer, Game> games;

  public OnlineGames() {
    this.games = new HashMap<Integer, Game>();
  }

  public void addGame(Game game) {
    games.put(game.getId(), game);
  }

  public Game getGame(int id) {
    return games.get(id);
  }

  public void deleteGame(int joinCode) {
    games.remove(joinCode);
  }
}
