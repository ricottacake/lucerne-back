package com.aceliq.lucerne.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.aceliq.lucerne.model.Game;
import com.aceliq.lucerne.model.Protocol;

@Component
public class SendToWebsocket {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  public <T> void send(String receiver, T data) {
    simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/reply", data);
  }

  public <T> void sendToAllPlayers(Game game, Protocol<T> protocol) {
    simpMessagingTemplate.convertAndSend("/topic/game/" + game.getId(), protocol);
  }
}
