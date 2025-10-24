package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    Hand hand;
    String username;
    private final boolean bot;

    public Player(String username, boolean isBot) {
        this.bot = isBot;
        hand = new Hand();
        this.username = username.replaceAll("[^a-zA-Z0-9 _]","");
    }

    public Player(String username) {
        hand = new Hand();
        this.username = username.replaceAll("[^a-zA-Z0-9 _]","");
        this.bot = false; // default human
    }

    public boolean isBot() {
        return bot;
    }

    public boolean isWin() {
        return (hand.numCardsInHand() == 0);
    }

    public void action(int cardHandIndex) {
        if (cardHandIndex >= 1 && cardHandIndex <= hand.numCardsInHand()) {
            Card card = hand.getCardFromHand(cardHandIndex, true);
            Game.discardPile.addCardToPile(card);
        } else if (cardHandIndex == hand.numCardsInHand() + 1) {
            hand.addCard(Game.drawPile.getTopCard(true));
        } else {
            // Worry about reprompting later
            throw new Error("You typed an invalid value!");
        }
    }

    // --------------------------- BOT CODE -------------------------------------------

    public void makeBotMove() {
        List<Integer> scores = calculateCardScores();

        int bestScore = 0;

        for (int score : scores) {
            if (score > bestScore) bestScore = score;
        }

        if (bestScore == 0) {
            action(hand.numCardsInHand() + 1);
            return;
        }

        action(scores.indexOf(bestScore));
    }


    public List<Integer> calculateCardScores() {
        if (bot) {
            Card currentCardOnDeck = Game.discardPile.getTopCard(false);
            List<Integer> playableCards = hand.getPlayableCards();
            return getScores(playableCards, currentCardOnDeck);
        }
        return new ArrayList<>(Collections.nCopies(hand.getHand().size(), 0));
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
