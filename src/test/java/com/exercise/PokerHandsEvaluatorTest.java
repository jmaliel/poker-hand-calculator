package com.exercise;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.exercise.exception.PokerHandEvaluatorException;

public class PokerHandsEvaluatorTest {

    private PokerHandsEvaluator evaluator;
    
    @Before
    public void setUp(){
        evaluator = new PokerHandsEvaluator();
    }
    
    @Test
    public void testPokerHands_OutputMultipleBestHands() throws PokerHandEvaluatorException, IOException{
        evaluator.performPokerHandsEvaluationAndListTheBest("src/test/resources/multiple-best-hands.txt");
    }
    
    @Test
    public void testPokerHands_OutputBestHand() throws PokerHandEvaluatorException, IOException{
        evaluator.performPokerHandsEvaluationAndListTheBest("src/test/resources/bestHand.txt");
    }
    
    @Test
    public void testPokerHands_BestHand() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/bestHand.txt", true);
        
        assertEquals("H,A|H,K|H,Q|H,10|H,J", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.STRAIGHT_FLUSH, pokerHands.get(0).getRule());
        
        assertEquals("H,K|S,K|D,K|C,K|H,10", pokerHands.get(1).getOriginalInput());
        assertEquals(PokerRule.FOUR_OF_A_KIND, pokerHands.get(1).getRule());
        
        assertEquals("C,6|H,6|D,6|H,K|C,K", pokerHands.get(2).getOriginalInput());
        assertEquals(PokerRule.FULL_HOUSE, pokerHands.get(2).getRule());
        
        assertEquals("H,K|H,Q|H,7|H,10|H,9", pokerHands.get(3).getOriginalInput());
        assertEquals(PokerRule.FLUSH, pokerHands.get(3).getRule());
        
        assertEquals("H,K|C,Q|D,J|S,10|H,9", pokerHands.get(4).getOriginalInput());
        assertEquals(PokerRule.STRAIGHT, pokerHands.get(4).getRule());
        
        assertEquals("H,3|S,3|D,A|C,3|C,7", pokerHands.get(5).getOriginalInput());
        assertEquals(PokerRule.THREE_OF_KIND, pokerHands.get(5).getRule());
        
        assertEquals("H,7|C,7|D,10|S,10|H,9", pokerHands.get(6).getOriginalInput());
        assertEquals(PokerRule.TWO_PAIR, pokerHands.get(6).getRule());
        
        assertEquals("D,K|H,Q|H,7|S,7|H,9", pokerHands.get(7).getOriginalInput());
        assertEquals(PokerRule.ONE_PAIR, pokerHands.get(7).getRule());
        
        assertEquals("D,K|H,Q|H,3|S,7|H,9", pokerHands.get(8).getOriginalInput());
        assertEquals(PokerRule.NO_PAIR, pokerHands.get(8).getRule());
    }
    
    @Test
    public void testPokerHands_MultipleBestHands() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/multiple-best-hands.txt", true);
        assertEquals("H,A|S,A|D,A|C,A|H,J", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.FOUR_OF_A_KIND, pokerHands.get(0).getRule());
        
        assertEquals("H,K|S,K|D,K|C,K|H,10", pokerHands.get(1).getOriginalInput());
        assertEquals(PokerRule.FOUR_OF_A_KIND, pokerHands.get(1).getRule());
    }
    
    @Test
    public void testPokerHands_StraightFlush() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/straight-flush.txt", true);
        assertEquals("H,A|H,K|H,Q|H,10|H,J", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.STRAIGHT_FLUSH, pokerHands.get(0).getRule());
    }
    
    @Test
    public void testPokerHands_FourOfAKind() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/four-of-a-kind.txt", true);
        assertEquals("H,K|S,K|D,K|C,K|H,10", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.FOUR_OF_A_KIND, pokerHands.get(0).getRule());
    }
    
    @Test
    public void testPokerHands_FullHouse() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/full-house.txt", true);
        assertEquals("C,6|H,6|D,6|H,K|C,K", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.FULL_HOUSE, pokerHands.get(0).getRule());
    }
    
    @Test
    public void testPokerHands_Flush() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/flush.txt", true);
        assertEquals("H,K|H,Q|H,7|H,10|H,9", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.FLUSH, pokerHands.get(0).getRule());
    }
    
    @Test
    public void testPokerHands_Straight() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/straight.txt", true);
        assertEquals("H,K|C,Q|D,J|S,10|H,9", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.STRAIGHT, pokerHands.get(0).getRule());
    }
    
    @Test
    public void testPokerHands_ThreeOfAKind() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/three-of-a-kind.txt", true);
        assertEquals("H,3|S,3|D,A|C,3|C,7", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.THREE_OF_KIND, pokerHands.get(0).getRule());
    }
    
    @Test
    public void testPokerHands_TwoPair() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/two-pair.txt", true);
        assertEquals("H,7|C,7|D,10|S,10|H,9", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.TWO_PAIR, pokerHands.get(0).getRule());
    }
    
    @Test
    public void testPokerHands_OnePair() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/one-pair.txt", true);
        assertEquals("D,K|H,Q|H,7|S,7|H,9", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.ONE_PAIR, pokerHands.get(0).getRule());
    }
    
    @Test
    public void testPokerHands_NoPair() throws PokerHandEvaluatorException, IOException{
        List<PokerHand> pokerHands = evaluator.performPokerHandsEvaluation("src/test/resources/no-pair.txt", true);
        assertEquals("D,K|H,Q|H,3|S,7|H,9", pokerHands.get(0).getOriginalInput());
        assertEquals(PokerRule.NO_PAIR, pokerHands.get(0).getRule());
    }
}
