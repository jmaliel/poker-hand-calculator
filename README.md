# poker-hand-calculator
## Problem:
Design a method which compares 2 poker hands and determine which one is the higher rank.

## Poker Rules - ranked in order of the best
    STRAIGHT_FLUSH -  1
    FOUR_OF_A_KIND -  2
    FULL_HOUSE - 3
    FLUSH -  4
    STRAIGHT - 5
    THREE_OF_KIND - 6
    TWO_PAIR - 7
    ONE_PAIR - 8
    NO_PAIR -  9
    
## Scope changes made:
This is a simple utility to evaluate a list of poker hands and output the best hand or hands. 

## Additional Production Level Considerations:
### 1 - Rules need to be more stringent.
Current version is working under the premise of the 9 defined poker rules. However we haven't considered the various edge cases that can occur in order to truly determine the best poker hand.
Some additional lower order poker rules:
1. If two hands we are comparing end up with same rule, the hand with the higher card value should be considered as the best hand.
2. If two hands we are comparing end up with the same rule and suppose the card values match (applicable in One-Pair, Two-Pair), then the remaining card needs to be considered to determine who has the best hand.
Note: there might be more lower order rules I haven't listed but are still applicable.

These lower order rules can be made part of the PokerHandsProcessor class. However we need to enhance the code slightly to indicate which hand is better.  Currently the Poker rules are ranked according to the definition above. So the application will need to provide a secondary ranking to indicate which hand is the best.

### 2 - Validate the Poker hands list and the cards in a hand.
Current version validates:
1. Does the number of cards in the hand total 5?
2. Is the suit and the value pair provided for a card valid?
In a production system you would only be evaluating a list of cards from a single card deck.  This system allows for poker hands from more than one deck by not keeping track of the cards that have been seen.
This is a major flaw within this tool.  In order to implement this, the tool will need to only allow for cards from one deck to be evaluated.  The tool will need to first load a full card deck when this utility starts up. Then when a list of poker hands is given to the tool to be evaluated it can map the card seen in the list to the card deck in memory.
Further evalaution of the poker hand will then be more accurate.

Caveat: The card validation and lower level rules depends on the style of poker that is being evaluated.  For example, 5 Card Draw has a slightly different set of rules than Texas Hold'em.

### 3 - Logging
Current version includes some info logging when a poker hand is evaluated.  However, the tool will fare better in a production system if more debug logging is added such that any errors can be easily diagnosed.

### 4 - Deployment
Current version is a standalone jar that takes a file(containing poker hands list) as input.  This is to demonstrate the poker hand calculator functionality.  However, in a production system this can serve as a library or it might be beneficial to deploy this as a REST web service.  
In either case, the entry point into the poker hand calculator API will need to be modified and only a single instance of the PokerHandsEvaluator should be made for the consumer. Furthermore, the poker hands list input and the response from an evaluation will not need to be modified according to the requirements of the deployment.  To deploy this as a web service, a web service framework(Spring, Jersey, Restlet, etc.) can be used to implement the service endpoint.  The service requirements will need to be further identified to better serve the consumer.

## Prerequisites
JDK 1.8.0_121 or higher. Maven 3.3.9 or higher

## Build
```
## creates the .classpath and .project files to run this project within Eclipse. 
## also downloads the necessary dependencies from maven central.
$ mvn clean eclipse:eclipse 
```

```
## creates a two jars after compiling and running unittests.
$ mvn clean package 
```

## Running from command prompt
```
$ java -jar poker-hand-calculator-1.0-jar-with-dependencies.jar <options> <path/to/poker/hands/list>
```
```
Options are:
-a  : output all the poker hand with the associated poker rules
-as : output all the poker hands sorted by poker rules rank
-b  : output the best poker hand or hands in the list
Example:
java -jar poker-hand-calculator-1.0-jar-with-dependencies.jar  -as pokerHandsList.txt
The poker hands list file is expected to contain:
suit,card-value pairs delimited by |
Each line in the file is expected to have 5 pairs.
For example:
H,A|S,A|D,A|C,A|H,J
Where:
The suits are represented by: H - Hearts, S - Spades, C - Clubs, D - Diamonds
The values are represented by: A - Ace, K - King, Q - Queen, J - Jack and 2 to 10
```

```
Sample test files can be found in /src/test/resources
```

## Sample Outputs:
```
{ poker-hand-calculator } master » java -jar poker-hand-calculator-1.0-jar-with-dependencies.jar -as bestHand.txt
2017-12-12 22:21:29 INFO  PokerHandsEvaluator:79 - Starting poker hand evaluation...
2017-12-12 22:21:29 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (C,K), (C,6), (H,6), (D,6)]
2017-12-12 22:21:29 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,K), (H,Q), (H,9), (H,7), (S,7)]
2017-12-12 22:21:29 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (H,Q), (H,10), (H,9), (H,7)]
2017-12-12 22:21:29 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (C,Q), (D,J), (S,10), (H,9)]
2017-12-12 22:21:29 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,K), (H,Q), (H,9), (S,7), (H,3)]
2017-12-12 22:21:29 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,A), (H,K), (H,Q), (H,J), (H,10)]
2017-12-12 22:21:29 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (S,K), (D,K), (C,K), (H,10)]
2017-12-12 22:21:29 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,A), (C,7), (H,3), (S,3), (C,3)]
2017-12-12 22:21:29 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,10), (S,10), (H,9), (H,7), (C,7)]
2017-12-12 22:21:29 INFO  PokerHandsEvaluator:95 - Completed poker hand evaluation...
Results:
Poker Hand: H,A|H,K|H,Q|H,10|H,J         ---> Poker Rule: Straight Flush
Poker Hand: H,K|S,K|D,K|C,K|H,10         ---> Poker Rule: Four of a Kind
Poker Hand: C,6|H,6|D,6|H,K|C,K          ---> Poker Rule: Full House
Poker Hand: H,K|H,Q|H,7|H,10|H,9         ---> Poker Rule: Flush
Poker Hand: H,K|C,Q|D,J|S,10|H,9         ---> Poker Rule: Straight
Poker Hand: H,3|S,3|D,A|C,3|C,7          ---> Poker Rule: Three of a Kind
Poker Hand: H,7|C,7|D,10|S,10|H,9        ---> Poker Rule: Two Pair
Poker Hand: D,K|H,Q|H,7|S,7|H,9          ---> Poker Rule: One Pair
Poker Hand: D,K|H,Q|H,3|S,7|H,9          ---> Poker Rule: No Pair
```
```
{ poker-hand-calculator } master » java -jar poker-hand-calculator-1.0-jar-with-dependencies.jar -a bestHand.txt
2017-12-12 22:21:33 INFO  PokerHandsEvaluator:79 - Starting poker hand evaluation...
2017-12-12 22:21:33 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (C,K), (C,6), (H,6), (D,6)]
2017-12-12 22:21:33 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,K), (H,Q), (H,9), (H,7), (S,7)]
2017-12-12 22:21:33 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (H,Q), (H,10), (H,9), (H,7)]
2017-12-12 22:21:33 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (C,Q), (D,J), (S,10), (H,9)]
2017-12-12 22:21:33 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,K), (H,Q), (H,9), (S,7), (H,3)]
2017-12-12 22:21:33 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,A), (H,K), (H,Q), (H,J), (H,10)]
2017-12-12 22:21:33 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (S,K), (D,K), (C,K), (H,10)]
2017-12-12 22:21:33 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,A), (C,7), (H,3), (S,3), (C,3)]
2017-12-12 22:21:33 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,10), (S,10), (H,9), (H,7), (C,7)]
2017-12-12 22:21:33 INFO  PokerHandsEvaluator:95 - Completed poker hand evaluation...
Results:
Poker Hand: C,6|H,6|D,6|H,K|C,K          ---> Poker Rule: Full House
Poker Hand: D,K|H,Q|H,7|S,7|H,9          ---> Poker Rule: One Pair
Poker Hand: H,K|H,Q|H,7|H,10|H,9         ---> Poker Rule: Flush
Poker Hand: H,K|C,Q|D,J|S,10|H,9         ---> Poker Rule: Straight
Poker Hand: D,K|H,Q|H,3|S,7|H,9          ---> Poker Rule: No Pair
Poker Hand: H,A|H,K|H,Q|H,10|H,J         ---> Poker Rule: Straight Flush
Poker Hand: H,K|S,K|D,K|C,K|H,10         ---> Poker Rule: Four of a Kind
Poker Hand: H,3|S,3|D,A|C,3|C,7          ---> Poker Rule: Three of a Kind
Poker Hand: H,7|C,7|D,10|S,10|H,9        ---> Poker Rule: Two Pair
```
```
{ poker-hand-calculator } master » java -jar poker-hand-calculator-1.0-jar-with-dependencies.jar -b bestHand.txt
2017-12-12 22:21:40 INFO  PokerHandsEvaluator:79 - Starting poker hand evaluation...
2017-12-12 22:21:40 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (C,K), (C,6), (H,6), (D,6)]
2017-12-12 22:21:40 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,K), (H,Q), (H,9), (H,7), (S,7)]
2017-12-12 22:21:40 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (H,Q), (H,10), (H,9), (H,7)]
2017-12-12 22:21:40 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (C,Q), (D,J), (S,10), (H,9)]
2017-12-12 22:21:40 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,K), (H,Q), (H,9), (S,7), (H,3)]
2017-12-12 22:21:40 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,A), (H,K), (H,Q), (H,J), (H,10)]
2017-12-12 22:21:40 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(H,K), (S,K), (D,K), (C,K), (H,10)]
2017-12-12 22:21:40 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,A), (C,7), (H,3), (S,3), (C,3)]
2017-12-12 22:21:40 INFO  PokerHandsProcessor:28 - Evaluating poker hand: [(D,10), (S,10), (H,9), (H,7), (C,7)]
2017-12-12 22:21:40 INFO  PokerHandsEvaluator:95 - Completed poker hand evaluation...
Results:
Poker Hand: H,A|H,K|H,Q|H,10|H,J         ---> Poker Rule: Straight Flush
```