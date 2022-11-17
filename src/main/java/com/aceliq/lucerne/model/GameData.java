package com.aceliq.lucerne.model;

public class GameData {

  private boolean timeIsOk;
  private Card card;
  private String result;
  private String playerQueryType;

  public String getPlayerQueryType() {
    return playerQueryType;
  }

  public void setPlayerQueryType(String playerQueryType) {
    this.playerQueryType = playerQueryType;
  }

  public boolean isTimeIsOk() {
    return timeIsOk;
  }

  public void setTimeIsOk(boolean timeIsOk) {
    this.timeIsOk = timeIsOk;
  }

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
