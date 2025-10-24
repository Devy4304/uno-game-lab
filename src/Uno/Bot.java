package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bot extends Player {
    public Bot(String username) {
        super(username);
    }

    public List<Integer> calculateCardScores() {
        Card currentCardOnDeck = Game.discardPile.getTopCard(false);
        List<Integer> playableCards = hand.getPlayableCards();
        return getScores(playableCards, currentCardOnDeck);
    }

    private List<Integer> getScores(List<Integer> playableCards, Card currentCardOnDeck) {
        List<Integer> scores = new ArrayList<>(Collections.nCopies(hand.getHand().size(), 0));

        for (int index : playableCards) {
            int cardNum = hand.getHand().get(index).getCardNum();
            Card.Colors cardColor = hand.getHand().get(index).getCardColor();

            int score = 5;
            score += (cardNum > 9) ? 20 : 10;
            score += (cardNum >= 13) ? -10 : 5;
            score += (cardColor == currentCardOnDeck.getCardColor()) ? 8 : 0;
            score += (cardNum == currentCardOnDeck.getCardNum()) ? 6 : 0;

            scores.set(index, score);
        }
        return scores;
    }
}
