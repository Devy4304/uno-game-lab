package Uno;

import java.util.*;

/**
 * The Hand class represents a collection of cards a player holds in a card game.
 * It includes methods for managing the hand, such as adding or removing cards,
 * determining playable cards, and calculating hand-specific statistics.
 */
public class Hand {
    private final List<Card> hand = new ArrayList<>();
    // private int latestPlayedCardIndex;

    /**
     * Constructs a new Hand object and initializes it with a default set of cards.
     * This constructor populates the hand with seven cards drawn from the game's draw pile.
     * Each card is drawn from the top of the draw pile using the `getTopCard` method,
     * which optionally removes the card from the deck after retrieval.
     */
    public Hand() {
        for (int i = 0; i < 7; i++) {
            hand.add(Game.drawPile.getTopCard(true));
        }
    }

    /**
     * Constructs a new Hand object with a specified number of starting cards.
     * This constructor populates the hand by drawing the specified number of
     * cards from the game's draw pile. Each card is drawn from the top of the draw
     * pile using the `getTopCard` method, which optionally removes the card from
     * the deck after retrieval.
     *
     * @param numStartingCards the number of cards to draw from the draw pile to initialize the hand
     */
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
            if (removeAfterGet) {
                hand.remove(index);
                // latestPlayedCardIndex = index;
            };
            return card;
        } else {
            // Actually do something here
            throw new Error("Something terrible has occurred. (Tried to play from an empty hand, which should have been a win)");
        }
    }

    // public void clearLatestIndex() {
    //     latestPlayedCardIndex = 0;
    // }

    /**
     * Determines the color that is most frequently used by the cards in the hand.
     * Wild cards (cards with a number greater than or equal to 13) are ignored when counting.
     *
     * @return the color that is used by the highest number of cards in the hand
     */
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

    /**
     * Retrieves a list of indices representing the cards in the hand that can
     * be played on the current top card of the discard pile.
     * A card is considered playable if it shares the same number or color as
     * the top card, or if it is a wild card (e.g., card number >= 13).
     *
     * @return a list of integers, where each integer represents the index of a
     *         playable card in the hand. The list is empty if no cards are playable.
     */
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
     * Generates a string representation of the hand, indicating which cards can
     * be played and the available actions. Playable cards are marked with their
     * index (starting from 1), and unplayable cards are marked with a placeholder.
     * If no cards are playable, an option to draw a card from the draw pile is added.
     *
     * @return a string representing the current state of the hand, including
     *         playable and unplayable cards along with possible actions.
     */
    public String toString() {
        StringBuilder out = new StringBuilder();
        List<Integer> playableCards = getPlayableCards();
        int i = 0;
        for (Card card : hand) {
            // if (i == latestPlayedCardIndex) out.append(Utility.Console.Colors.GREEN);
            if (playableCards.contains(i)) out.append(i + 1).append(") ").append(Utility.Console.Colors.RESET).append(card).append(";");
            else out.append("-) ").append(card).append(";");
            i++;
        }
        if (playableCards.isEmpty()) {
            out.append(i+1).append(") Take Card from Draw Pile");
        }
        return String.valueOf(out);
    }
}
