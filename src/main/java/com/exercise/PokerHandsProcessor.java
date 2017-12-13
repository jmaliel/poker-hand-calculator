package com.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to evaluate a poker hand. Given a list of 5 cards, this class
 * will determine which {@link PokerRule} can be associated with it.
 * 
 * @author Jacob Maliel
 *
 */
public class PokerHandsProcessor {
    private static Logger LOGGER = LoggerFactory.getLogger(PokerHandsProcessor.class);

    /**
     * Evaluates the given poker hand and returns the poker rule it falls under.
     * @param cards the list of cards in the poker hand.
     * @return a {@link PokerRule}
     */
    public static PokerRule evaluateCards(List<Card> cards) {
        LOGGER.info("Evaluating poker hand: "+cards);
        
        if(cards.size() != 5){
            LOGGER.info("A poker hand can only contain 5 cards...");
            return null;
        }
        if(cards.stream().distinct().count() != 5){
            LOGGER.info("A poker hand cannot contain duplicate cards...");
            return null;
        }
        
        Map<String,List<Card>> valueToCardsMap = findDuplicatesBySuit(cards);
        
        //check if the list of cards contain pairs
        //if the map has less than 5 keys then a pair exists.
        if(valueToCardsMap.keySet().size() < 5){
            //if the map only has 2 keys, then we either have a four of a kind or a full house
            if (valueToCardsMap.keySet().size() == 2) {
                for(Entry<String, List<Card>> entry : valueToCardsMap.entrySet()){
                    //if any of the entry's card list has a size of 4, then it is a Four of a kind.
                    if(entry.getValue().size() == 4){
                        return PokerRule.FOUR_OF_A_KIND;
                    }
                }
                //if there were no card sizes that had size 4, then we have a full house.
                return PokerRule.FULL_HOUSE;
            }else if (valueToCardsMap.keySet().size() == 3) {
                for(Entry<String, List<Card>> entry : valueToCardsMap.entrySet()){
                    //if any of the entry's card list has a size of 3, then it is a Three of a kind.
                    if(entry.getValue().size() == 3){
                        return PokerRule.THREE_OF_KIND;
                    }
                }
                //if there were no card sizes that had size 3, then we have a two pair.
                return PokerRule.TWO_PAIR;
            }else if (valueToCardsMap.keySet().size() == 4) {
                return PokerRule.ONE_PAIR;
            }
        }else{
        //not a pair
            //check if the cards are in numeric order
            if(isSequentiallyDesc(cards)){
              //check to see if the same suit
                if(isSameSuit(cards)){
                    return PokerRule.STRAIGHT_FLUSH;
                }else{
                    return PokerRule.STRAIGHT;
                }
            }else{
                //check to see if the same suit
                if(isSameSuit(cards)){
                    return PokerRule.FLUSH;
                }else{
                    return PokerRule.NO_PAIR;
                }
            }
            
        }
        
        //we have an erroneous result
        return null;
    }
    
    /*
     * Method that determines the the cards in the poker hand are ordered
     * sequentially descending.
     */
    static boolean isSequentiallyDesc(List<Card> cards){
        if(cards == null || cards.isEmpty()){
            return false;
        }
        
        Integer currentCardValue = null;
        for(Card card : cards){
            //set the top card.
            if(currentCardValue == null){
                currentCardValue = card.getNumericValue();
                continue;
            }
            if(card.getNumericValue() == currentCardValue-1){
                //reset the current card.
                currentCardValue = card.getNumericValue();
            }else{
                //the cards are not ordered sequentially descending.
                return false;
            }
        }
        //if we were able to go through the entire loop then the current card hand is sequential ordered in a descending manner.
        return true;
    }
    
    /*
     * Determines if all the cards in the are of the same suit.
     */
    static boolean isSameSuit(List<Card> cards){
        if(cards == null || cards.isEmpty()){
            return false;
        }
        
        Suit topCardSuit = null;
        for(Card card : cards){
            //set the top card.
            if(topCardSuit == null){
                topCardSuit = card.getSuit();
                continue;
            }
            if(card.getSuit() != topCardSuit){
                //saw a card that doesn't have the same suit
                return false;
            }
        }
        //if we were able to go through the entire loop then all the cards have the same suit
        return true;
    }

    /*
     * Creates a map that is keyed by card value. The purpose of this is Map is
     * to identify duplicate cards by suit in the poker hand.
     */
    static Map<String,List<Card>> findDuplicatesBySuit(List<Card> cards) {
        Map<String,List<Card>> pairs = new HashMap<String, List<Card>>();
        if(cards == null || cards.isEmpty()){
            return pairs;
        }
        for(Card card : cards){
            List<Card> duplicates = pairs.get(card.getStringValue());
            if(duplicates == null){
                duplicates = new ArrayList<Card>();
                pairs.put(card.getStringValue(), duplicates);
            }
            duplicates.add(card);
        }
        return pairs;
    }
}
