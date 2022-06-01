package blackjack;

public class Player {
    private final String Name;
    Card[] cardsInHand = new Card[11];
    int numberOfCardsInHand;
    int finalScore = 0;
    boolean ifBlackJack = false;
    boolean ifBusted = false;

    public Player(String name) {
        Name = name;
        numberOfCardsInHand = 0;
    }

    public String getName() {
        return Name;
    }

    int calculateScore(){
        int score = 0;
        for (int thisCard = 0; thisCard <cardsInHand.length ; thisCard++){
            if(cardsInHand[thisCard] != null){
                score += cardsInHand[thisCard].getValue();
            }
        }
        return score;
    }

    void checker(){
        if(calculateScore() > 21) {
            ifBusted = true;
            ifBlackJack = false;
        }else if (calculateScore() == 21){
            ifBlackJack = true;
        }
    }

    void addCard(Card newCard){
        cardsInHand[numberOfCardsInHand] = new Card(newCard.getSuit(), newCard.getRank(), newCard.getValue());
        numberOfCardsInHand++;
    }

    public String playerData() {
        return "Player{" +
                "Name='" + Name + '\'' +
                ", score=" + calculateScore() +
                ", numberOfCardsInHand=" + (numberOfCardsInHand) +
                ", ifBlackJack=" + ifBlackJack +
                ", ifBusted=" + ifBusted +
                '}';
    }
}
