package com.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.exercise.exception.PokerHandEvaluatorException;

public class PokerHandsProcessorTest {

    @Test
    public void testPokerHand_StraightFlush() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"K"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"Q"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"J"));
        Collections.sort(cards);
        assertEquals(PokerRule.STRAIGHT_FLUSH, PokerHandsProcessor.evaluateCards(cards));
    }
    
    @Test
    public void testPokerHand_InvalidCardsSize_Long() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"K"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"Q"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"J"));
        cards.add(new Card(CardIdentifier.getSuit("D"),"J"));
        Collections.sort(cards);
        assertEquals(null, PokerHandsProcessor.evaluateCards(cards));
    }
    
    @Test
    public void testPokerHand_InvalidCardsSize_Short() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"K"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"Q"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"10"));
        Collections.sort(cards);
        assertEquals(null, PokerHandsProcessor.evaluateCards(cards));
    }
    
    @Test
    public void testPokerHand_DuplicateCards() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"K"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"Q"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        Collections.sort(cards);
        assertEquals(null, PokerHandsProcessor.evaluateCards(cards));
    }
    
    @Test
    public void testPokerHand_CheckSortedDescending() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"K"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"Q"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"J"));
        Collections.sort(cards);
        assertTrue(PokerHandsProcessor.isSequentiallyDesc(cards));
    }
    
    @Test
    public void testPokerHand_CheckSortedDescending_Invalid() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"K"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"Q"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"J"));
        assertFalse(PokerHandsProcessor.isSequentiallyDesc(cards));
    }
    
    @Test
    public void testPokerHand_CheckSameSuit() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"K"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"Q"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"J"));
        assertTrue(PokerHandsProcessor.isSameSuit(cards));
    }
    
    @Test
    public void testPokerHand_CheckContainsDifferentSuit() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"K"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"Q"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("D"),"J"));
        assertFalse(PokerHandsProcessor.isSameSuit(cards));
    }
    
    @Test
    public void testPokerHand_FindDuplicates_Null() throws PokerHandEvaluatorException{
        assertTrue(PokerHandsProcessor.findDuplicatesBySuit(null).isEmpty());
    }
    
    @Test
    public void testPokerHand_FindDuplicates_Three() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("D"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("S"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"J"));
        assertEquals(3, PokerHandsProcessor.findDuplicatesBySuit(cards).size());
    }
    
    @Test
    public void testPokerHand_FindDuplicates_Four() throws PokerHandEvaluatorException{
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardIdentifier.getSuit("H"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("D"),"A"));
        cards.add(new Card(CardIdentifier.getSuit("C"),"2"));
        cards.add(new Card(CardIdentifier.getSuit("S"),"10"));
        cards.add(new Card(CardIdentifier.getSuit("H"),"J"));
        assertEquals(4, PokerHandsProcessor.findDuplicatesBySuit(cards).size());
    }
    
    @Test
    public void testPokerHand_InvalidCardValue() throws PokerHandEvaluatorException{
        try{
            List<Card> cards = new ArrayList<Card>();
            cards.add(new Card(CardIdentifier.getSuit("H"),"1"));
            cards.add(new Card(CardIdentifier.getSuit("D"),"A"));
            cards.add(new Card(CardIdentifier.getSuit("C"),"2"));
            cards.add(new Card(CardIdentifier.getSuit("S"),"10"));
            cards.add(new Card(CardIdentifier.getSuit("H"),"J"));
            Collections.sort(cards);
            PokerHandsProcessor.evaluateCards(cards);
            fail("Invalid numeric card value...");
        }catch(Exception e){
            assertEquals("Unable to determine numeric value for card...", e.getMessage());
        }
    }
    
    @Test
    public void testPokerHand_IsSameSuit_Null() throws PokerHandEvaluatorException{
        assertFalse(PokerHandsProcessor.isSameSuit(null));
    }
    
    @Test
    public void testPokerHand_IsSameSuit_Empty() throws PokerHandEvaluatorException{
        assertFalse(PokerHandsProcessor.isSameSuit(Collections.<Card>emptyList()));
    }
    
    @Test
    public void testPokerHand_IsSequentiallyDesc_Null() throws PokerHandEvaluatorException{
        assertFalse(PokerHandsProcessor.isSequentiallyDesc(null));
    }
    
    @Test
    public void testPokerHand_IsSequentiallyDesc_Empty() throws PokerHandEvaluatorException{
        assertFalse(PokerHandsProcessor.isSequentiallyDesc(Collections.<Card>emptyList()));
    }
}
