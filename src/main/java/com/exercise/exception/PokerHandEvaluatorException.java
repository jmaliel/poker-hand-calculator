package com.exercise.exception;

/**
 * Exception handler for the PokerHandEvaluator
 * 
 * @author Jacob Maliel
 *
 */
public class PokerHandEvaluatorException extends Exception{

    private static final long serialVersionUID = -5327968520605537972L;
    
    public PokerHandEvaluatorException(String message){
        super(message);
    }
    
    public PokerHandEvaluatorException(String message, Throwable cause){
        super(message, cause);
    }
}
