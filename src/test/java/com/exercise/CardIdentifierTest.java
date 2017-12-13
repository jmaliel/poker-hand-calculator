package com.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.exercise.exception.PokerHandEvaluatorException;

public class CardIdentifierTest {

    @Test
    public void testInvalidCardValue() throws PokerHandEvaluatorException{
        try{
            CardIdentifier.getNumericValue("1");
            fail("Invalid numeric card value...");
        }catch(Exception e){
            assertEquals("Unable to determine numeric value for card...", e.getMessage());
        }
    }
    
    @Test
    public void testInvalidCardSuit() throws PokerHandEvaluatorException{
        try{
            CardIdentifier.getSuit("A");
            fail("Invalid numeric card suit...");
        }catch(Exception e){
            assertEquals("Unable to determine suit...", e.getMessage());
        }
    }
    
    @Test
    public void testInvalidCardSuit_MultipleChars() throws PokerHandEvaluatorException{
        try{
            CardIdentifier.getSuit("AA");
            fail("Invalid numeric card suit...");
        }catch(Exception e){
            assertEquals("Unable to determine suit...", e.getMessage());
        }
    }
    
    @Test
    public void testInvalidCardSuit_NullString() throws PokerHandEvaluatorException{
        try{
            CardIdentifier.getSuit(null);
            fail("Should fail with null or empty message.");
        }catch(Exception e){
            assertEquals("Symbol for suit cannot be null or empty...", e.getMessage());
        }
    }

    @Test
    public void testInvalidCardSuit_EmptyString() throws PokerHandEvaluatorException{
        try{
            CardIdentifier.getSuit("");
            fail("Should fail with null or empty message.");
        }catch(Exception e){
            assertEquals("Symbol for suit cannot be null or empty...", e.getMessage());
        }
    }
    
    @Test
    public void testInvalidCardValue_NullString() throws PokerHandEvaluatorException{
        try{
            CardIdentifier.getNumericValue(null);
            fail("Should fail with null or empty message.");
        }catch(Exception e){
            assertEquals("Symbol for card value cannot be null or empty...", e.getMessage());
        }
    }

    @Test
    public void testInvalidCardValue_EmptyString() throws PokerHandEvaluatorException{
        try{
            CardIdentifier.getNumericValue("");
            fail("Should fail with null or empty message.");
        }catch(Exception e){
            assertEquals("Symbol for card value cannot be null or empty...", e.getMessage());
        }
    }
    
    @Test
    public void testValidCardValue() throws PokerHandEvaluatorException{
        assertEquals(new Integer(14), CardIdentifier.getNumericValue("A"));
        assertEquals(new Integer(13), CardIdentifier.getNumericValue("K"));
        assertEquals(new Integer(12), CardIdentifier.getNumericValue("Q"));
        assertEquals(new Integer(11), CardIdentifier.getNumericValue("J"));
        assertEquals(new Integer(10), CardIdentifier.getNumericValue("10"));
    }
    
    @Test
    public void testValidCardSuit() throws PokerHandEvaluatorException{
        assertEquals(Suit.Hearts, CardIdentifier.getSuit("H"));
        assertEquals(Suit.Clubs, CardIdentifier.getSuit("C"));
        assertEquals(Suit.Diamonds, CardIdentifier.getSuit("D"));
        assertEquals(Suit.Spades, CardIdentifier.getSuit("S"));
    }
}
