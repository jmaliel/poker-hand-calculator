package com.exercise;

/**
 * Represents a Suit in a playing card deck.
 * 
 * @author Jacob Maliel
 *
 */
public enum Suit {
    Hearts("H"),
    Diamonds("D"),
    Clubs("C"),
    Spades("S");
    
    private String symbol;
    
    Suit(String symbol){
        this.symbol = symbol;
    }
    
    public String getSymbol(){
        return this.symbol;
    }
}
