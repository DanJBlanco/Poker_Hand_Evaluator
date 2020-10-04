package com.daniel.pokerhand.util;

import com.daniel.pokerhand.model.Card;
import com.daniel.pokerhand.model.Hand;
import com.daniel.pokerhand.model.Hand.Type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class HandValue {

    /***
     *  [2,3,4,5,....,Q,K,A] = 13 slots
     */
    private static final int ENCODE_BASE = Card.Rank.A.getVal() +1 ;

    /***
     *  indexes = 2
     *  rank, repeat
     *  -       -
     * | 3 ,   ♥|
     * | 2 ,   ♦|
     * | Q ,   ♠|
     * | A ,   ♦|
     * |10 ,   ♣|
     * -        -
     */
    private static final int INDIXES_LENGTH = 2 ;

    private static final int RANK_INDEX = 0 ;
    private static final int REPEAT_INDEX = 1 ;

    private static final Type[][] MATRIX_TYPE= {
            {Type.HIGH_CARD                         },  // 5 indexes
            {Type.ONE_PAIR,         Type.TWO_PAIR   },  // 3,4 indexes
            {Type.THREE_OF_A_KIND,  Type.FULL_HOUSE },  // 2,3 indexes
            {Type.FOUR_OF_A_KIND                    }   // 2 indexes
    };


    /***
     * {
     *     {3, ♥},
     *     {2, ♦},
     *     {Q, ♣},
     *     {A, ♦},
     *     {10,♠},
     * }
     */
    private final int[][] indexes = new int[Hand.CARDS_HANDS][INDIXES_LENGTH];

    /***
     * {
     *     2:
     *     3:
     *     .
     *     .
     *     .
     *     K:
     *     A:
     * }
     *
     * {
     *     ♥:
     *     ♦:
     *     ♣:
     *     ♠:
     * }
     */
    private final int[] ranks = new int[ENCODE_BASE];
    private final int[] suits = new int[Card.Suit.values().length];


    public boolean isStraight = false;
    public boolean isFlush = false;


    public int evalHand(Card[] cards){

        getInitialData(cards);

//        return calculateHandValurOrdinal();
        return encodeValue(getType(isStraight, isFlush, indexes), indexes);
    }

    private void getInitialData(Card[] cards) {

        Arrays.fill(ranks,0);
        Arrays.fill(suits,0);

        int index = 0;

        // Fill Arrays ranks, suits
        for(Card card: cards){
            ranks[card.getRank()]++;
            suits[card.getSuit().ordinal()]++;
        }


        // Se evalua si todas las cartas son del mismo palo
        isFlush = suits[cards[0].getSuit().ordinal()] == Hand.CARDS_HANDS;



        isStraight = getIsStraight(ranks);

    }

    private Type getType(boolean isStraight, boolean isFlush, int[][] indexes) {
        Type type = null;

        if(isStraight){
            type = isFlush ? getIsRoyalFlush() : Type.STRAIGHT ;

        } else if ( isFlush){
            type = Type.FLUSH;
        } else {
            type = MATRIX_TYPE
                    [indexes[0][REPEAT_INDEX] - 1 ]
                    [indexes[1][REPEAT_INDEX] - 1 ];
        }
        return type;
    }


    private int calculateHandValurOrdinal() {
        Type type = null;

        if(isStraight){
            type = isFlush ? getIsRoyalFlush() : Type.STRAIGHT ;

        } else if ( isFlush){
            type = Type.FLUSH;
        } else {
            type = MATRIX_TYPE
                    [indexes[0][REPEAT_INDEX] - 1 ]
                    [indexes[1][REPEAT_INDEX] - 1 ];
        }
        return encodeValue(type, indexes);
    }

    private Type getIsRoyalFlush() {

        if ( indexes[Hand.CARDS_HANDS-1][RANK_INDEX] == Card.Rank.T.getVal() ){
            return Type.ROYAL_FLUSH;
        }
        else{
            return Type.STRAIGHT_FLUSH;
        }

    }

    private static int encodeValue(Type type, int[][] indexes) {

        int result = type.ordinal();

        int i = 0, j = 0;

        while (j < Hand.CARDS_HANDS){
            for (int k = 0; k < indexes[i][REPEAT_INDEX]; k++){
                result = result * ENCODE_BASE + indexes[i][RANK_INDEX];
                j++;
            }
            i++;
        }

        return result;

    }

    private boolean getIsStraight(int[] ranks) {

        int straightCounter = 0 ;
        // Indice de orden de los pares de cartas
        int j = 0;

        for(int i = ranks.length -1 ; i>=0; i--){
            if(ranks[i] > 0){

                straightCounter++;

                indexes[j][RANK_INDEX] = i;
                indexes[j][REPEAT_INDEX] = ranks[i];;
                upIndex(j++);

            } else{
                straightCounter = 0;
            }
        }

        return (straightCounter == Hand.CARDS_HANDS) ||  checkStraight5ToAce(straightCounter);

    }


    // Actualizar el orden de las cartas( de mayor repetecion y de mayor valor a menor)
    private void upIndex(int i) {
        int k = i;

        while( k > 0 && indexes[k-1][REPEAT_INDEX] < indexes[k][REPEAT_INDEX]){
            int[] temp = indexes[k-1];
            indexes[k-1] = indexes[k];
            indexes[k] = temp;
            k--;
        }

    }
    private boolean checkStraight5ToAce(int straightCounter) {

        boolean straight5toA= false;

        // Se evalua que exista un Ace en las 5 cartas
        // Se evalua que exista 4 cartas seguidas siendo el 2 la ultima
        if( ranks[Card.Rank.A.getVal()] == 1 && straightCounter == Hand.CARDS_HANDS -1){
            straight5toA = true;

            //reordenando los indices
            for ( int i = 1; i < indexes.length; i++){
                indexes[i-1][RANK_INDEX] = indexes[i][RANK_INDEX];
            }
            indexes[indexes.length-1][RANK_INDEX] = Card.Rank.A.getVal();

        }

        return straight5toA;
    }

    public double getTypeValue(Card[] cards) {

        getInitialData(cards);
        return getType(isStraight, isFlush, indexes).getProbability();
    }
}
