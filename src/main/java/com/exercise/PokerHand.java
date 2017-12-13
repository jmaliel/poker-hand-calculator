package com.exercise;

import java.util.List;

/**
 * A POJO for a Poker hand.
 * @author Jacob Maliel
 *
 */
public class PokerHand implements Comparable<PokerHand>{
    private List<Card> cards;
    private String originalInput;
    private PokerRule rule;
    
    public PokerHand(List<Card> cards, String originalInput){
        this.cards = cards;
        this.originalInput = originalInput;
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getOriginalInput() {
        return originalInput;
    }

    public PokerRule getRule() {
        return rule;
    }

    public void setRule(PokerRule rule) {
        this.rule = rule;
    }
    
    @Override
    public int compareTo(PokerHand other) {
        if(this.rule.getRank() > other.rule.getRank()){
            return 1;
        }else if(this.rule.getRank() < other.rule.getRank()){
            return -1;
        }
        return 0;
    };
    
    public String toString(){
        return "Poker Hand: "+originalInput+" \t ---> Poker Rule: "+rule.getRuleName();
    }
}
