package Uno;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> hand = new ArrayList<>();

    /**
     * Constructs a new Hand instance by drawing 7 cards from the provided deck.
     * Each card is drawn from the top of the deck and added to the hand.
     *
     * @param deck the deck of cards to draw cards from
     */
    public Hand(Deck deck) {
        for (int i = 0; i < 7; i++) {
            hand.add(deck.getTopCard(true));
        }
    }

    /**
     * Constructs a new Hand instance by drawing a specified number of cards from the provided deck.
     * Each card is drawn from the top of the deck and added to the hand.
     *
     * @param deck the deck of cards to draw cards from
     * @param numStartingCards the number of cards to draw from the deck to initialize the hand
     */
    public Hand(Deck deck, int numStartingCards) {
        for (int i = 0; i < numStartingCards; i++) {
            hand.add(deck.getTopCard(true));
        }
    }

    /**
     * Retrieves the list of cards currently in the hand.
     *
     * @return a list containing the cards in the hand.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Retrieves the total number of cards currently in the hand.
     *
     * @return the number of cards in the hand.
     */
    public int numCardsInHand() {
        return hand.size();
    }

    /**
     * Retrieves a card from the hand at the specified index.
     * If the removeAfterGet parameter is true, the card will also
     * be removed from the hand.
     *
     * @param index the position of the card to retrieve from the hand
     * @param removeAfterGet a boolean flag indicating whether the card should
     *                       be removed from the hand after retrieval
     * @return the card at the specified index
     * @throws Error if the hand is empty when trying to retrieve a card
     */
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

    /**
     * Adds a new card to the current hand.
     *
     * @param newCard the card to be added to the hand
     */
    public void addCard(Card newCard) {
        hand.addLast(newCard);
    }

    /**
     * Returns a string representation of the hand, listing all cards currently in it.
     *
     * @return a string describing the cards in the hand.
     */
    public String toString() {
        StringBuilder out = new StringBuilder();
        int i = 1;
        for (Card card: hand) {
            out.append(i).append(") ").append(card).append(";");
            i++;
        }
        return String.valueOf(out);
    }
}
