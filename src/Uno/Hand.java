package Uno;

import java.util.*;

public class Hand {
    private final List<Card> hand = new ArrayList<>();

    public Hand() {
        for (int i = 0; i < 7; i++) {
            hand.add(Game.drawPile.getTopCard(true));
        }
    }

    public Hand(int numStartingCards) {
        for (int i = 0; i < numStartingCards; i++) {
            hand.add(Game.drawPile.getTopCard(true));
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

    public Card.Colors getColorThatTheMostCardsUse() {
        // Yay!!!! EnumMap!!!!!!
        Map<Card.Colors, Integer> count = new EnumMap<>(Card.Colors.class);

        for (Card card : hand) {
            Card.Colors c = card.getCardColor();
            if (card.getCardNum() >= 13) continue; // If it's wild, don't count it as whatever it is
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        // THIS MAKES TOTAL SENSE!!!!
        return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /**
     * Adds a new card to the current hand.
     *
     * @param newCard the card to be added to the hand
     */
    public void addCard(Card newCard) {
        hand.addLast(newCard);
    }

    public List<Integer> getPlayableCards() {
        List<Integer> playableCards = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).canPlayCard(Game.discardPile.getTopCard(false))) {
                playableCards.add(i);
            }
        }
        return playableCards;
    }

    /**
     * Returns a string representation of the hand, listing all cards currently in it.
     *
     * @return a string describing the cards in the hand.
     */
    public String toString() {
        StringBuilder out = new StringBuilder();
        List<Integer> playableCards = getPlayableCards();
        int i = 0;
        for (Card card: hand) {
            if (playableCards.contains(i)) out.append(i + 1).append(") ").append(card).append(";");
            else out.append("-) ").append(card).append(";");
            i++;
        }
        if (playableCards.isEmpty()) {
            out.append(i+1).append(") Take Card from Draw Pile");
        }
        return String.valueOf(out);
    }
}
