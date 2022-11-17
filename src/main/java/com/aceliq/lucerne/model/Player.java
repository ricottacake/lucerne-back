package com.aceliq.lucerne.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Player object creates when user create or join to game
 * 
 *
 */

public class Player extends Gambler {

  /**
   * 
   * user contain User object which contain info about client in our service
   * 
   */
  private final User user;

  /**
   * result field contain result your game
   * 
   * true - you win false - you lose
   * 
   */
  private boolean result;

  /**
   * 
   * contain your status in game
   * 
   * it is use for check when any user send query in game and we can check it is valid or not
   * 
   * true - your move now false - not your move
   */
  @JsonIgnore
  private boolean myMove;

  /**
   * 
   * it is thread creates when player move begins and creates new every one when user moving
   * 
   */
  @JsonIgnore
  private MetaSend thread;

  /**
   * the status of your participation in the game true - the turn is not over or started false - you
   * finish your move
   */
  @JsonIgnore
  private boolean endGame;

  /**
   * create player using user object
   * 
   * @param user - User object which contain name, country, date registration, playerId, publicId
   */
  public Player(User user) {
    this.user = user;
    this.myMove = false;
    this.endGame = false;
  }

  public User getUser() {
    return user;
  }

  public boolean isMyMove() {
    return myMove;
  }

  public void setMyMove(boolean myMove) {
    this.myMove = myMove;
  }

  public MetaSend getThread() {
    return thread;
  }

  public void setThread(MetaSend thread) {
    this.thread = thread;
  }

  public boolean isEndGame() {
    return endGame;
  }

  public void setEndGame(boolean endGame) {
    this.endGame = endGame;
  }

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }
}

