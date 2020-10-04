package com.daniel.pokerhand.model;

import java.util.Arrays;

public class Hand {

    public enum Type {
        HIGH_CARD(50.1177),
        ONE_PAIR(42.2569),
        TWO_PAIR(4.7539),
        THREE_OF_A_KIND(2.1128),
        STRAIGHT(0.3925),
        FLUSH(0.1965),
        FULL_HOUSE(0.1441),
        FOUR_OF_A_KIND(0.0240),
        STRAIGHT_FLUSH(0.00139),
        ROYAL_FLUSH(0.000154);

        private Type(double probability){
            this.probability = probability;
        }
        public double getProbability(){
            return 100.000000 - this.probability;
        }

        private final double probability;
    }

    public static final int CARDS_HANDS = 5;

    public int value;

    private Card[] cards;

    public Hand(){

    }

    public Hand(Card[] cards) {
        this.cards = cards;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + Arrays.toString(cards) +
                '}';
    }

    public static Hand[] getHandByString(String each){

        String[] data = each.split(" ");

        Hand[] hands = new Hand[]{
                new Hand(new Card[]{
                        new Card(data[0]),
                        new Card(data[1]),
                        new Card(data[2]),
                        new Card(data[3]),
                        new Card(data[4]),
                }),
                new Hand(new Card[]{
                        new Card(data[5]),
                        new Card(data[6]),
                        new Card(data[7]),
                        new Card(data[8]),
                        new Card(data[9]),
                })
        };

        return hands;
    }

}
