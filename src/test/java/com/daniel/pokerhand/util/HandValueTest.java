package com.daniel.pokerhand.util;

import com.daniel.pokerhand.model.Card;
import com.daniel.pokerhand.model.Hand;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HandValueTest {


    // hand 1
    @Test
    public void pair_8_win_pair_5(){
        String stringHands = "5h 5c 6s 7s kd 2c 3s 8s 8d td";

        Hand[] hands = getHandByString(stringHands);

        int[] values = getValues(hands);

        assertThat(values[0]<values[1], CoreMatchers.is(true));

    }


    // hand 2
    @Test
    public void hight_card_ace_queen(){
        String stringHands = "5D 8C 9S JS AC 2C 5C 7D 8S QH";
        Hand[] hands = getHandByString(stringHands);
        int[] values = getValues(hands);
        assertThat(values[0]>values[1], CoreMatchers.is(true));
    }

    // hand 3
    @Test
    public void flush_win_three_Aces(){
        String stringHands = "2D 9C AS AH AC 3D 6D 7D TD QD";
        Hand[] hands = getHandByString(stringHands);
        int[] values = getValues(hands);
        assertThat(values[0]<values[1], CoreMatchers.is(true));
    }

    // hand 4
    @Test
    public void pair_neither_queens_hight_card_9_win_seven(){
        String stringHands = "4D 6S 9H QH QC 3D 6D 7H QD QS";
        Hand[] hands = getHandByString(stringHands);
        int[] values = getValues(hands);
        assertThat(values[0]>values[1], CoreMatchers.is(true));
    }


    @Test
    public void full_house_neither_with_4_win_3(){
        String stringHands = "2H 2D 4C 4D 4S 3C 3D 3S 9S 9D";
        Hand[] hands = getHandByString(stringHands);
        int[] values = getValues(hands);
        assertThat(values[0]>values[1], CoreMatchers.is(true));
    }



    public Hand[] getHandByString(String each){

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


    private int[] getValues(Hand[] hands) {


        HandValue handEvaluator = new HandValue();

        return new int[]{
                handEvaluator.evalHand(hands[0].getCards()),
                handEvaluator.evalHand(hands[1].getCards())
        };


    }


}