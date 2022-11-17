package com.aceliq.lucerne.model;

import org.springframework.beans.factory.annotation.Autowired;
import com.aceliq.lucerne.components.GameService;
import com.aceliq.lucerne.components.SendToWebsocket;

public class MetaSend extends Thread {

  private Player player;
  private Game game;
  private boolean status;

  @Autowired
  private GameService gameService;

  @Autowired
  private SendToWebsocket sendToWebsocket;

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public MetaSend(Player player, Game game) {
    this.player = player;
    this.game = game;
    this.status = true;
  }

  public void run() {

    for (int i = 0; i < 5 && status; i++) {

      sendToWebsocket.send(player.getUser().getId(), i);

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        return;
      }
    }

    if (!status) {
      return;
    }

    GameData gameData = new GameData();
    gameData.setTimeIsOk(false);

    sendToWebsocket.send(this.player.getUser().getId(),
        new Protocol<GameData>("ok", "gameData", gameData));

    this.player.setMyMove(false);
    this.player.setEndGame(true);

    gameService.nextPlayer(game);
  }
}
