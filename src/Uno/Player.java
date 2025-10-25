package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private final Hand hand;
    private final String username;
    private final boolean bot;
    private Card latestPlayedCard;
    private boolean lastActionWasDraw;

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

    public String getUsername() {
        return username;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean isWin() {
        return (hand.numCardsInHand() == 0);
    }

    public void action(int cardHandIndex) {
        if (cardHandIndex >= 0 && cardHandIndex < hand.numCardsInHand()) {
            Card card = hand.getCardFromHand(cardHandIndex, true);
            Game.discardPile.addCardToPile(card);
            latestPlayedCard = card;
            lastActionWasDraw = false;
        } else if (cardHandIndex == hand.numCardsInHand() + 1) {
            hand.addCard(Game.drawPile.getTopCard(true));
            latestPlayedCard = null;
            lastActionWasDraw = true;
        } else {
            // Worry about reprompting later
            throw new Error("You typed an invalid value!" + cardHandIndex);
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
            hand.addCard(Game.drawPile.getTopCard(true));
            lastActionWasDraw = true;
            latestPlayedCard = null;
            return;
        }

        if (hand.getCardFromHand(scores.indexOf(bestScore), false).getCardNum() >= 13) {
            hand.getCardFromHand(scores.indexOf(bestScore), false).setWildColor(hand.getColorThatTheMostCardsUse());
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

    public String getLatestPlayedCard() {
        if (lastActionWasDraw) return "Drew a card";
        return (latestPlayedCard != null) ? latestPlayedCard.getColoredCardText(true) : "No card played yet";
    }
}
