package com.exercise;

/**
 * A defined set of poker rules that will be associated with a poker hand.
 * 
 * @author Jacob Maliel
 *
 */
public enum PokerRule{
    STRAIGHT_FLUSH("Straight Flush", 1),
    FOUR_OF_A_KIND("Four of a Kind", 2),
    FULL_HOUSE("Full House", 3),
    FLUSH("Flush", 4),
    STRAIGHT("Straight", 5),
    THREE_OF_KIND("Three of a Kind", 6),
    TWO_PAIR("Two Pair", 7),
    ONE_PAIR("One Pair", 8),
    NO_PAIR("No Pair", 9);
    
    private String ruleName;
    private int rank;
    
    PokerRule(String ruleName, int rank){
        this.ruleName = ruleName;
        this.rank = rank;
    }
    
    public String getRuleName(){
        return this.ruleName;
    }
    
    public int getRank(){
        return this.rank;
    }
}
