package com.aceliq.lucerne.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public abstract class Gambler {

  @JsonView(Views.Detailed.class)
  protected List<Card> cards;

  public void addCard(Card card) {
    this.cards.add(card);
  }

  public Integer checkSum() {
    return cards.stream().reduce(0, (x, y) -> x + y.getCost(), (x, y) -> x + y);
  }

  public Gambler() {
    this.cards = new ArrayList<Card>();
  }

  public List<Card> getCards() {
    return cards;
  }
}
