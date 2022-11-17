package com.aceliq.lucerne.components;

import com.aceliq.lucerne.model.Card;

public class FirstTwoDealerCards {

  private Card card1;
  private Card card2;

  public Card getCard1() {
    return card1;
  }

  public void setCard1(Card card1) {
    this.card1 = card1;
  }

  public Card getCard2() {
    return card2;
  }

  public void setCard2(Card card2) {
    this.card2 = card2;
  }

  public FirstTwoDealerCards(Card card1, Card card2) {
    super();
    this.card1 = card1;
    this.card2 = card2;
  }
}
