package com.aceliq.lucerne.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import com.aceliq.lucerne.components.GameService;
import com.aceliq.lucerne.components.OnlineGames;
import com.aceliq.lucerne.model.Game;
import com.aceliq.lucerne.model.PlayerQuery;
import com.aceliq.lucerne.model.Protocol;

@Controller
public class WebsocketController {

  @Autowired
  private GameService gameService;

  @Autowired
  private ApplicationContext context;

  @Autowired
  private OnlineGames onlineGames;

  public WebsocketController() {}

  @EventListener
  private void handleSessionDisconnect(SessionDisconnectEvent event) {
    gameService.handlerWebsocketDisconnect(event.getSessionId());
  }

  public Game getGameAndAddPlayer(int gameId) {

    Game game = onlineGames.getGame(gameId);
    return game;
  }

  public Game createGame(int gameId) {

    Game game = (Game) context.getBean("getGame", gameId);
    onlineGames.addGame(game);

    return game;
  }

  @EventListener
  private void handleSessionConnect(SessionConnectedEvent event) {}

  @MessageMapping("/newGame")
  public void newGame(SimpMessageHeaderAccessor sha, @Payload Protocol<String> protocol)
      throws Exception {
    gameService.lobbyAction(sha.getSessionId(), protocol);
  }

  @MessageMapping("/joinGame")
  public void joinGame(SimpMessageHeaderAccessor sha, @Payload Protocol<Integer> protocol)
      throws Exception {
    gameService.lobbyAction(sha.getSessionId(), protocol);
  }

  @MessageMapping("/transferGameData")
  public void transferGameData(SimpMessageHeaderAccessor sha,
      @Payload Protocol<PlayerQuery> protocol) throws Exception {
    gameService.handlerGameData(sha.getSessionId(), protocol);
  }
}
