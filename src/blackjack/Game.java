package blackjack;

import java.util.Random;

public class Game {
    Player[] playersInGame = new Player[4]; // player[3] is the dealer
    Card[] deckOfCards = new Card[52];
    boolean isEnded = false;
    static final int numberOfTurns = 9; // (9 turns + 2 cards) for each player
    int gameScore = 0;

    public void beginTheGame(String[] playersNames) {
        generateDeckOfCards();
        for (int i=0;i< playersInGame.length;i++){
            playersInGame[i] = new Player(playersNames[i]);
            playersInGame[i].addCard(drawCard());
            playersInGame[i].addCard(drawCard());
        }
    }

    void generateDeckOfCards(){
        for (int i=0 ; i<4 ; i++){          // suit 0 - suit 1  - suit 2  - suit 3
            for (int j=0; j<13 ; j++){      // 0...12 - 13...25 - 26...38 - 39...51
                if (j > 9){
                    deckOfCards[(i*13)+j] = new Card(i,j,10);
                }else{
                    deckOfCards[(i*13)+j] = new Card(i,j,j+1);
                }
            }
        }
    }

    Card drawCard(){
        Random randomNumberGenerator = new Random();
        int randomCardNumber;
        Card drawnCard = null;
        while (true) {
            randomCardNumber = randomNumberGenerator.nextInt(52);
            if (deckOfCards[randomCardNumber] != null) {
                drawnCard = deckOfCards[randomCardNumber];
                deckOfCards[randomCardNumber] = null;
                break;
            }
        }
        return drawnCard;
    }

    void updateScores(int numberOfPlayersToUpdateScores){
        for (int i=0;i< numberOfPlayersToUpdateScores;i++){
            playersInGame[i].checker();
            if(!playersInGame[i].ifBusted) {
                if(playersInGame[i].ifBlackJack){
                    gameScore = 21;
                }else{
//                    System.out.println("Player "+i+" // his Score "+ playersInGame[i].calculateScore() + " // gameScore" + gameScore);
                    if (gameScore < playersInGame[i].calculateScore() && playersInGame[i].calculateScore() < 21) {
                        gameScore = playersInGame[i].calculateScore();
                    }
                }
            }
        }
    }

    public void gameData() {
//        for (int i=0;i<52;i++){
//            if(deckOfCards[i] != null)
//                System.out.println((i+1) + "- " + deckOfCards[i].cardData());
//        }
//        System.out.println("-------------------------------------------------------------------------------------");
        for (int i=0;i<4;i++){
            System.out.println((i+1) + "- " + playersInGame[i].playerData());
            System.out.println((i+1) + "***Player Cards in his hands***");
            for (int j=0; j<11; j++){
                System.out.println(j+1);
                if(playersInGame[i].cardsInHand[j] != null)
                System.out.println("\t"+(j+1) + "- " + playersInGame[i].cardsInHand[j].cardData());
            }
        }
    }
}
