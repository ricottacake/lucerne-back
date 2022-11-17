package com.aceliq.lucerne.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {

  private List<Card> deck;

  public Deck() {
    Card[] cards = {Card.ACE, Card.TWO, Card.THREE, Card.FOUR, Card.FIVE, Card.SIX, Card.SEVEN,
        Card.EIGHT, Card.NINE, Card.TEN, Card.JACK, Card.QUEEN, Card.KING, Card.ACE, Card.TWO,
        Card.THREE, Card.FOUR, Card.FIVE, Card.SIX, Card.SEVEN, Card.EIGHT, Card.NINE, Card.TEN,
        Card.JACK, Card.QUEEN, Card.KING, Card.ACE, Card.TWO, Card.THREE, Card.FOUR, Card.FIVE,
        Card.SIX, Card.SEVEN, Card.EIGHT, Card.NINE, Card.TEN, Card.JACK, Card.QUEEN, Card.KING,
        Card.ACE, Card.TWO, Card.THREE, Card.FOUR, Card.FIVE, Card.SIX, Card.SEVEN, Card.EIGHT,
        Card.NINE, Card.TEN, Card.JACK, Card.QUEEN, Card.KING};
    List<Card> cardList = new ArrayList<Card>(Arrays.asList(cards));// Arrays.asList(cards);
    Collections.shuffle(cardList);
    this.deck = cardList;
  }

  public Card getCard() {
    return this.deck.remove(0);
  }
}
