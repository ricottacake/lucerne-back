package com.aceliq.lucerne.model;

public enum Card {

  ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(
      2), QUEEN(3), KING(4);

  private final int cost;

  private Card(int cost) {
    this.cost = cost;
  }

  public int getCost() {
    return cost;
  }
}
