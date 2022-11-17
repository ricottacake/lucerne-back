package com.aceliq.lucerne.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * 
 * Class for game Creates when user create new game
 * 
 * @author orandela
 *
 */
public class Game {

  /**
   * unique id game contain 6 digits
   */
  private int id;

  /**
   * 
   * Random generated deck for this game
   */
  private Deck deck;

  /**
   * 
   * List all online players in this game or game lobby
   */
  private List<Player> players;

  /**
   * 
   * Dealer entity for this game
   */
  @JsonIgnore
  private Dealer delaer;

  /**
   * 
   * Create game entity
   * 
   * @param gameId - random 6 digits id game also used for join other players
   * 
   */
  public Game(int gameId) {
    this.deck = new Deck();
    this.id = gameId;
    this.delaer = new Dealer();
    this.players = new ArrayList<Player>();
  }

  public Integer getId() {
    return id;
  }

  @JsonIgnore
  public Card getCard() {
    return deck.getCard();
  }

  public Dealer getDelaer() {
    return delaer;
  }

  public Player getPlayerById(String id) {
    return players.stream().filter(iteratorPlayer -> iteratorPlayer.getUser().getId().equals(id))
        .findFirst().get();
  }

  public void removePlayer(Player player) {
    this.players.remove(player);
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public List<Player> getPlayers() {
    return players;
  }
}
