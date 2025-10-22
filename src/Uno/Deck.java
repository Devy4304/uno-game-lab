package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    /*
    Every deck has one 0 of every color,
    Two copies of each number between 1-9 of each color,
    Two copies of Skip, Reverse and Draw Two for every color,
    One Wild card for each color,
    One Wild Draw 4 card for each color.
     */
    private List<Card> deck;

    public Deck() {
        this.deck = shuffleDeck(generateDeck());
    }

    /**
     * Randomly shuffles the given deck of cards.
     *
     * @param deck the list of cards to be shuffled
     * @return the shuffled deck of cards
     */
    private static List<Card> shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
        return deck;
    }

    /**
     * Generates a new deck of Uno cards with the standard distribution of cards.
     * This includes numbered cards (0-9), special action cards (Draw Two, Skip, Reverse),
     * and wild cards (Wild, Wild Draw 4) for each applicable color.
     * <p>
     * Each deck contains one 0 card for each color, two copies of each numbered card
     * (1-9) for each color, two copies of each Draw Two, Skip, and Reverse card for
     * each color, and single instances of Wild and Wild Draw 4 cards.
     *
     * @return a list of cards representing a complete Uno deck.
     */
    private static List<Card> generateDeck() {
        // A Uno deck has 108 cards
        List<Card> deck = new ArrayList<Card>(108);
        // Iterate over all colors, RED, YELLOW, GREEN and BLUE
        for (Card.Colors color : Card.Colors.values()) {
            // Iterate through every type of card
            for (int i = 0; i <= 14; i++) {
                // If it is a 1-9 or Draw Two or Skip or Reverse, add two of them
                if (i >= 1 && i <= 12) {
                    deck.add(new Card(i, color));
                    deck.add(new Card(i, color));
                } else {
                    // Otherwise, add only one card
                    deck.add(new Card(i, color));
                }
            }
        }
        // Output the deck
        return deck;
    }

    /**
     * Retrieves the top card of the deck. Optionally removes it from the deck.
     *
     * @param removeAfterGet if true, the top card will be removed from the deck after being retrieved.
     * @return the top card of the deck.
     */
    public Card getTopCard(boolean removeAfterGet) {
        // Get the top card
        Card card = deck.getFirst();
        // Remove it if wanted
        if (removeAfterGet) deck.removeFirst();
        // Return that top card
        return card;
    }

    /**
     * Resets the deck to its initial state by generating a new deck and shuffling it.
     * This ensures the deck is reset and randomized when the deck runs out.
     */
    public void resetDeck() {
        deck = shuffleDeck(generateDeck());
    }
}
