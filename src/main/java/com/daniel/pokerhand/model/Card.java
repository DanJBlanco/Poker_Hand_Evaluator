package com.daniel.pokerhand.model;

public class Card {



    public static enum Suit{
        S("SPADE"), H("HEART"), D("DIAMOND"), C("CLUB");

        private Suit(String c){
            this.c = c;
        }

        public String getC(){
            return c;
        };

        private final String c;

    }

    public enum Rank {
        T(8), J(9), Q(10), K(11), A(12);
        private Rank(int value){
            this.value = value;
        }

        public int getVal(){
            return this.value;
        }

        private final int value;
    }

    private final int INDEX_DIFF = 2;

    private final Suit suit;
    private final int rank;
    private static final String STRING_RANK_CARDS = "23456789TJQKA" ;

    public Card(String code) {
        this.suit = Enum.valueOf(Suit.class, code.toUpperCase().substring(1));
        this.rank = code.substring(0,1).matches("\\d+") ? Integer.parseInt(code.substring(0,1)) - INDEX_DIFF : Enum.valueOf(Rank.class, code.substring(0,1).toUpperCase()).getVal();
    }

    public Card(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank(){
        return rank;
    }

    @Override
    public String toString() {

        return "Card{"+ rank + suit +'}';
    }
}
