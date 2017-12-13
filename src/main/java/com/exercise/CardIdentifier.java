package com.exercise;

import com.exercise.exception.PokerHandEvaluatorException;

/**
 * A utility class to determine the suit associated with a card or the value
 * associated with a card.
 * 
 * @author Jacob Maliel
 */
public class CardIdentifier {
    public static Suit getSuit(String symbol) throws PokerHandEvaluatorException{
        if(symbol == null || symbol.isEmpty()){
            throw new PokerHandEvaluatorException("Symbol for suit cannot be null or empty...");
        }
        Suit suit = null;
        if(symbol.length() == 1){
            switch(symbol){
            case "H":
                suit =  Suit.Hearts;
                break;
            case "D":
                suit =  Suit.Diamonds;
                break;
            case "C":
                suit =  Suit.Clubs;
                break;
            case "S" :
                suit =  Suit.Spades;
                break;
            }
        }
        if(suit == null){
            throw new PokerHandEvaluatorException("Unable to determine suit...");
        }
        return suit;
    }
    
    public static Integer getNumericValue(String symbol) throws PokerHandEvaluatorException{
        if(symbol == null || symbol.isEmpty()){
            throw new PokerHandEvaluatorException("Symbol for card value cannot be null or empty...");
        }
        Integer value = null;
        switch(symbol){
        case "A":
            value =  14;
            break;
        case "K":
            value =  13;
            break;
        case "Q" :
            value =  12;
            break;
        case "J" :
            value =  11;
            break;
        default:
            try{
                int possibleVal = Integer.parseInt(symbol);
                if(possibleVal <= 10 && possibleVal>1){
                    value = possibleVal;
                }
            }catch(NumberFormatException e){
                //This is expected, so no need to output it.
                //e.printStackTrace();
            }
            break;
        }
        if(value == null){
            throw new PokerHandEvaluatorException("Unable to determine numeric value for card...");
        }
        return value;
    }
}
