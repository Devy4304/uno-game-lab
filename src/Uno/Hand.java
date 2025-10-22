package Uno;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> hand = new ArrayList<>();

    public Hand(Deck deck) {
        for (int i = 0; i < 7; i++) {
            hand.add(deck.getTopCard(true));
        }
    }

    public Hand(Deck deck, int numStartingCards) {
        for (int i = 0; i < numStartingCards; i++) {
            hand.add(deck.getTopCard(true));
        }
    }

    public List<Card> getHand() {
        return hand;
    }

    public int numCardsInHand() {
        return hand.size();
    }

    public Card getCardFromHand(int index, boolean removeAfterGet) {
        if (!hand.isEmpty()) {
            Card card = hand.get(index);
            if (removeAfterGet) hand.remove(index);
            return card;
        } else {
            // Actually do something here
            throw new Error("Something terrible has occurred. (Tried to play from an empty hand, which should have been a win)");
        }
    }

    public void addCard(Card newCard) {
        hand.addLast(newCard);
    }

    public String toString() {
        StringBuilder out = new StringBuilder("Your hand has these cards: ");
        for (Card card: hand) {
            out.append(card.toString()).append(" ");
        }
        return String.valueOf(out);
    }
}
