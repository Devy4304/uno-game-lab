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

    public Deck(boolean fillWithCards) {
        if (fillWithCards) {
            this.deck = shuffleDeck(generateDeck());
        }
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
     * Generates a complete deck of Uno cards.
     * The deck consists of cards in all four colors (RED, YELLOW, GREEN, BLUE),
     * including numbered cards (0-9), special action cards (Draw Two, Reverse, Skip),
     * and one instance of cards with special functions (Wild, Wild Draw 4).
     *
     * @return a list containing all the cards in a standard Uno deck.
     */
    private static List<Card> generateDeck() {
        // A Uno deck has 108 cards
        List<Card> deck = new ArrayList<>(108);
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

    public void prepDiscardPile() {
        while (getTopCard(false).getCardNum() >= 13) {
            getTopCard(true);
        }
    }

    /**
     * Adds a specified card to the discard pile if the deck represents a discard pile.
     *
     * @param card the card to be added to the discard pile
     */
    public void addCardToPile(Card card) {
        deck.add(card);
    }

    /**
     * Retrieves the top card of the deck. Optionally removes it from the deck.
     *
     * @param removeAfterGet if true, the top card will be removed from the deck after being retrieved.
     * @return the top card of the deck.
     */
    public Card getTopCard(boolean removeAfterGet) {
        Card card;
        try {
            card = getCard(removeAfterGet);
        } catch (Exception _) {
            // If the deck is empty, refill.
            resetDeck();
            card = getCard(removeAfterGet);
        }
        return card;
    }

    // This is so I don't have the same code twice
    private Card getCard(boolean removeAfterGet) {
        // Get the top card
        Card card = deck.getLast();
        // Remove it if wanted
        if (removeAfterGet) deck.removeLast();
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
