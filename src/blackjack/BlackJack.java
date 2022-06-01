package blackjack;

import java.util.Scanner;

public class BlackJack {
    static Scanner input = new Scanner(System.in);
    static Game testGame = new Game();

    public static void main(String[] args) {
        /**/GUI gui = new GUI();
        initializeTheGame(testGame);
        /**/gui.runGUI(testGame.deckOfCards,
                   testGame.playersInGame[0].cardsInHand,
                   testGame.playersInGame[1].cardsInHand,
                   testGame.playersInGame[2].cardsInHand,
                   testGame.playersInGame[3].cardsInHand);

           gamePhasePlayers(testGame, gui);
           gamePhaseDealer(testGame, gui);
           testGame.updateScores(4);
//        testGame.gameData();                                                // Display game's data like every player's score, number of cards in hand, cards, ifBusted and ifBlackJack
           gamePhaseFinal(testGame);
        testGame.updateScores(4);
        System.out.println("***** Game Score = " + testGame.gameScore);           // Display game score
           System.out.println("--------------\n* Game Over *|\n--------------");
           System.out.println("Hope you enjoy :)");
   }


    static void initializeTheGame(Game gameInitializer){
        System.out.println("Welcome To BlackJack Game :)");
        System.out.println("Please Enter the Players' names");
        String[] inputPlayersNames = new String[4];
        for (int player = 0; player <inputPlayersNames.length ; player++){
            System.out.print("Enter Player " + (player +1) + " name: ");
            inputPlayersNames[player] = input.nextLine();
        }
        gameInitializer.beginTheGame(inputPlayersNames);
    }

    static void gamePhasePlayers(Game processGame, GUI gui){
        System.out.println("------------------------\n* Now The Game Starts *|\n------------------------");
        System.out.println("Your Choices is 1 for draw (Hit) or 2 for skip turn (Stand)");

        for (int player = 0; player < processGame.playersInGame.length-1 ; player++){

            System.out.printf("Player %d (%s) Turn...\n", player +1,processGame.playersInGame[player].getName());
            boolean skipped = false;

            for (int turn = 0; turn < Game.numberOfTurns ; turn++){

                if (skipped){
                    System.out.println((turn + 1) + ") ...");
                    continue;
                }

                while (true) {

                    System.out.print('=');
                    int choice = input.nextInt();

                    if (choice == 1) {
                        System.out.println((turn + 1) + ") Hit");
                        processGame.playersInGame[player].addCard(processGame.drawCard()); // bug!! removes the second one when adding the first new card
                        /**/gui.updatePlayerHand(processGame.playersInGame[player].cardsInHand[processGame.playersInGame[player].numberOfCardsInHand - 1], player);
                        processGame.updateScores(3);
//                        System.out.println("***** Game Score = " + testGame.gameScore);           // Display game score after each turn
                        break;

                    } else if (choice == 2) {

                        System.out.println((turn + 1) + ") Stand");
//                        System.out.println("***** Game Score = " + testGame.gameScore);           // Display game score after each turn
                        skipped = true;
                        break;

                    } else {
                        System.out.println("! WRONG ANSWER !");
                    }

                }
                processGame.updateScores(3);
//                System.out.println("turn " + (turn+1) + " ended");          // Display turn number that a player played
            }
            if(processGame.playersInGame[player].ifBlackJack){
                System.out.printf("Player %d (%s) Turn has ended ... got BlackJack\n", player +1,processGame.playersInGame[player].getName());
            }else if (processGame.playersInGame[player].ifBusted){
                processGame.gameScore = 0;
                processGame.updateScores(3);
                System.out.printf("Player %d (%s) Turn has ended ... got Busted\n", player +1,processGame.playersInGame[player].getName());
            }else{
                System.out.printf("Player %d (%s) Turn has ended ...\n", player +1,processGame.playersInGame[player].getName());
            }
//            System.out.println("***** Game Score after round = " + testGame.gameScore);           // Display game score after each round
        }
//        System.out.println("* Phase 1 Ended *");                           // Display That this function ended and 3 players turns finished
    }


    static void gamePhaseDealer(Game processGame, GUI gui){
//        int x=1;
        while (true){
//            System.out.println("dealer score = " + processGame.playersInGame[3].calculateScore() + "/ game score" + processGame.gameScore);   // Display dealer score before every draw
            if (processGame.playersInGame[0].ifBusted && processGame.playersInGame[1].ifBusted && processGame.playersInGame[2].ifBusted){
                break;
            }
            if ( processGame.playersInGame[3].calculateScore() <= processGame.gameScore & processGame.playersInGame[3].calculateScore() != 21 ){
                processGame.playersInGame[3].addCard(processGame.drawCard());
                gui.updateDealerHand(processGame.playersInGame[3].cardsInHand[processGame.playersInGame[3].numberOfCardsInHand - 1],processGame.deckOfCards);
//                processGame.updateScores(4);
//                System.out.println("Dealer Draw a Card number " +x);      // Display card number that dealer draw
//                x++;
            } else {
                processGame.updateScores(4);
//                System.out.println("Dealer loop broken");                // Display dealer loop finished
                break;
            }
        }
        if(processGame.playersInGame[3].ifBlackJack){
            System.out.printf("Dealer (%s) Turn has ended ... got BlackJack\n",processGame.playersInGame[3].getName());
        }else if (processGame.playersInGame[3].ifBusted){
            System.out.printf("Dealer (%s) Turn has ended ... got Busted\n",processGame.playersInGame[3].getName());
        }else{
            System.out.printf("Dealer (%s) Turn has ended ...\n",processGame.playersInGame[3].getName());
        }
//        System.out.println("Dealer Turn Ended");                          // Display dealer function finished
    }

    static void gamePhaseFinal(Game processGame){
        int numberOfPlayersWithHighestScore = 0;
        String winner = null;
        if (processGame.playersInGame[0].ifBusted && processGame.playersInGame[1].ifBusted && processGame.playersInGame[2].ifBusted){
            winner = processGame.playersInGame[3].getName();
            processGame.gameScore = processGame.playersInGame[3].calculateScore();
            System.out.println("** The Winner is " + winner + " **");
        } else {
            for (int player = 0; player < processGame.playersInGame.length ; player++){
                if (processGame.playersInGame[player].calculateScore() == processGame.gameScore){
                    winner = processGame.playersInGame[player].getName();
                    numberOfPlayersWithHighestScore++;
                }
            }
            if (numberOfPlayersWithHighestScore == 1){
                System.out.println("** The Winner is " + winner + " **");
            }else {
                System.out.println("** Push **");
            }
        }
    }
}
