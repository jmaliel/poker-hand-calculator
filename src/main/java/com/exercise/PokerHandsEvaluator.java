package com.exercise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import com.exercise.exception.PokerHandEvaluatorException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility that calculates the best Poker hand given a list of Poker Hands.
 * <p>
 * The Poker hands list file is expected to be in the following format:
 * <pre>
 * suit,card-value pairs delimited by |
 * </pre>
 * Each line in the file is expected to have 5 pairs.
 * For example:
 * <pre>
 * H,A|S,A|D,A|C,A|H,J
 * </pre>
 * <p>Where:</p>
 * <p>The suits are represented by: H - Hearts, S - Spades, C - Clubs, D - Diamonds</p>
 * <p>The values are represented by: A - Ace, K - King, Q - Queen, J - Jack and 2 to 10</p>
 * @author Jacob Maliel
 *
 */
public class PokerHandsEvaluator {
    private static Logger LOGGER = LoggerFactory.getLogger(PokerHandsEvaluator.class);
    
    public PokerHandsEvaluator(){
    }
    
    /**
     * Method to list the best poker hand in a list.
     * @param pokerHandsFilePath
     * @throws PokerHandEvaluatorException
     * @throws FileNotFoundException 
     */
    public void performPokerHandsEvaluationAndListTheBest(String pokerHandsFilePath)
            throws PokerHandEvaluatorException, IOException {
        List<PokerHand> pokerHands = performPokerHandsEvaluation(pokerHandsFilePath, true);

        // output the best hand(s) in the list
        List<PokerHand> bestHands = getBestHands(pokerHands);
        System.out.println("Results: ");
        for (PokerHand hand : bestHands) {
            System.out.println(hand.toString());
        }
    }
    
    /**
     * Method to list all the poker hand with the associated rules in the list.
     * @param pokerHandsFilePath
     * @throws PokerHandEvaluatorException
     * @throws FileNotFoundException 
     */
    public void performPokerHandsEvaluationAndListAll(String pokerHandsFilePath, boolean sortByRank)
            throws PokerHandEvaluatorException, IOException {
        List<PokerHand> pokerHands = performPokerHandsEvaluation(pokerHandsFilePath, sortByRank);

        // output all the hands
        System.out.println("Results: ");
        for (PokerHand hand : pokerHands) {
            System.out.println(hand.toString());
        }
    }

    List<PokerHand> performPokerHandsEvaluation(String pokerHandsFilePath, boolean sortByRank)
            throws PokerHandEvaluatorException, IOException {
        LOGGER.info("Starting poker hand evaluation...");
        
        // get the file input stream from the given file path.
        FileReader fr = getFileReader(pokerHandsFilePath);

        // load the poker hands in the file into memory
        // check if each line contains 5 cards per hand.
        List<PokerHand> pokerHands = loadPokerHands(fr);

        // associates a poker hand to a poker rule
        associatePokerRulesToPokerHands(pokerHands);

        if (sortByRank) {
            Collections.sort(pokerHands);
        }

        LOGGER.info("Completed poker hand evaluation...");
        return pokerHands;
    }
    
    private List<PokerHand> getBestHands(List<PokerHand> pokerHands) {
        //the poker hands should be sorted by rank when this method is called.
        List<PokerHand> bestHands = new ArrayList<PokerHand>();
        if(!pokerHands.isEmpty()){
            PokerHand bestHand = pokerHands.get(0);
            //get the list of best hands in the list.
            for(PokerHand hand : pokerHands){
                if(hand.getRule() == bestHand.getRule()){
                    bestHands.add(hand); 
                }else{
                    //break out of the loop now that all the best hands have been added.
                    break;
                }
            }
        }
        
        return bestHands;
    }

    private void associatePokerRulesToPokerHands(List<PokerHand> pokerHands) {
        for(PokerHand pokerHand : pokerHands){
            PokerRule rule = PokerHandsProcessor.evaluateCards(pokerHand.getCards());
            pokerHand.setRule(rule);
        }
    }

    private List<PokerHand> loadPokerHands(FileReader fr) throws PokerHandEvaluatorException, IOException {
        List<PokerHand> pokerHands = new ArrayList<PokerHand>();
        
        BufferedReader br = new BufferedReader(fr);
        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            String[] pokerHand = currentLine.split("\\|");
            if(pokerHand.length != 5){
                throw new PokerHandEvaluatorException("A poker hand must consist of 5 cards, the following hand list doesn't meet the 5 card criteria: "+currentLine);
            }
            List<Card> cards = new ArrayList<Card>(5);
            for(String suitCardPair : pokerHand){
                String[] pairArray = suitCardPair.split("\\,");
                if(pairArray.length != 2){
                    throw new PokerHandEvaluatorException("A card must contain the suit and the value, the following card["+suitCardPair+"] doesn't match the criteria.");
                }
                cards.add(new Card(CardIdentifier.getSuit(pairArray[0].trim().toUpperCase()), pairArray[1].trim().toUpperCase()));
            }
            
            //sort the cards in the hand.
            Collections.sort(cards);
            pokerHands.add(new PokerHand(cards,currentLine));
        }

        if (br != null) {
            br.close();
        }
        if (fr != null) {
            fr.close();
        }
        return pokerHands;
    }
    
    private FileReader getFileReader(String pokerHandsFilePath) throws PokerHandEvaluatorException, IOException {
        if(pokerHandsFilePath == null || pokerHandsFilePath.isEmpty()){
            throw new PokerHandEvaluatorException("File containing list of poker hands cannot be null or empty.");
        }
        
        File pokerHandsFile = new File(pokerHandsFilePath);
        if (pokerHandsFile.exists() && pokerHandsFile.isFile()) {
            FileReader fr = new FileReader(pokerHandsFile);
            return fr;
        }

        throw new PokerHandEvaluatorException(
                "Provided file[" + pokerHandsFilePath + "] doesn't exists or it is not a file.");
    }
    
    public static void main(String args[]){
        String USAGE = "This utility requires the poker hands list file as a parameter. \n" + 
                "Sample Usage: \n" +
                "java -jar poker-hand-calculator.jar <options> <path/to/poker/hands/list> \n"+
                "Options are: \n"+
                "-a  : output all the poker hand with the associated poker rules\n"+
                "-as : output all the poker hands with sorted by poker rules rank \n"+
                "-b  : output the best poker hand or hands in the list \n"+
                "Example: \n"+
                "java -jar poker-hand-calculator.jar  -as pokerHandsList.txt \n"+
                "The poker hands list file is expected to contain: \n"+
                "suit,card-value pairs delimited by | \n"+
                "Each line in the file is expected to have 5 pairs.\n"+
                "For example:\n"+
                "H,A|S,A|D,A|C,A|H,J\n"+
                "Where: \n"+
                "The suits are represented by: H - Hearts, S - Spades, C - Clubs, D - Diamonds\n"+
                "The values are represented by: A - Ace, K - King, Q - Queen, J - Jack and 2 to 10";

        // check to see if the poker hands list file is provided as an argument.
        if (args.length != 2) {
            System.out.println(USAGE);
        } else {
            PokerHandsEvaluator evaluator = new PokerHandsEvaluator();
            try{
                if("-a".equalsIgnoreCase(args[0])){
                    evaluator.performPokerHandsEvaluationAndListAll(args[1], false);
                }else if("-as".equalsIgnoreCase(args[0])){
                    evaluator.performPokerHandsEvaluationAndListAll(args[1], true);
                }else if("-b".equalsIgnoreCase(args[0])){
                    evaluator.performPokerHandsEvaluationAndListTheBest(args[1]);
                }else{
                    System.out.println(USAGE);
                }
            }catch(Exception e){
                //log the error if any issue occurred.
                LOGGER.error("Error occurred performing a poker hands evaluation...",e);
            }
        }
    }

}
