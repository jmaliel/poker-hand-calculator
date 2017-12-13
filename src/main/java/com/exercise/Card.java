package com.exercise;

import java.io.Serializable;

import com.exercise.exception.PokerHandEvaluatorException;

/**
 * A POJO for a playing card.
 * 
 * @author Jacob Maliel
 *
 */
public class Card implements Comparable<Card>, Serializable{

    private static final long serialVersionUID = 9032302663275026414L;
    
    private Suit suit;
    private String stringValue;
    private Integer numericValue;
    
    public Card(Suit suit, String stringValue) throws PokerHandEvaluatorException {
        this.suit = suit;
        this.stringValue = stringValue;
        this.numericValue = CardIdentifier.getNumericValue(stringValue);
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Integer getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(Integer numericValue) {
        this.numericValue = numericValue;
    }
    
    @Override
    public int compareTo(Card other) {
        if(this.numericValue > other.numericValue){
            return -1;
        }else if(this.numericValue < other.numericValue){
            return 1;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numericValue == null) ? 0 : numericValue.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (numericValue == null) {
            if (other.numericValue != null)
                return false;
        } else if (!numericValue.equals(other.numericValue))
            return false;
        if (suit != other.suit)
            return false;
        return true;
    }
    
    @Override
    public String toString(){
        return "("+suit.getSymbol()+","+stringValue+")";
    }
}
