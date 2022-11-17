package com.aceliq.lucerne.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.aceliq.lucerne.model.Game;
import com.aceliq.lucerne.model.MetaSend;
import com.aceliq.lucerne.model.Player;

@Service
public class PlayerService {

  @Autowired
  private ApplicationContext context;

  @Bean
  @Scope("prototype")
  public MetaSend getMetaSend(Player player, Game game) {
    return new MetaSend(player, game);
  }

  public void playerMove(Player player, Game game) {

    if (player.getThread() != null) {
      player.getThread().setStatus(false);
    }
    player.setThread((MetaSend) context.getBean("getMetaSend", player, game));
    player.getThread().start();
  }

  public void finishPlayerMove(Player player) {
    player.getThread().setStatus(false);
    player.setEndGame(true);
    player.setMyMove(false);
  }
}
