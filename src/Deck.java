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

    private static List<Card> shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
        return deck;
    }

    private static List<Card> generateDeck() {
        List<Card> deck = new ArrayList<Card>(108);
        for (Card.Colors color : Card.Colors.values()) {
            for (int i = 0; i <= 14; i++) {
                if (i >= 1 && i <= 12) {
                    deck.add(new Card(i, color));
                    deck.add(new Card(i, color));
                } else {
                    deck.add(new Card(i, color));
                }
            }
        }
        return deck;
    }

    public Card getTopCard(boolean removeAfterGet) {
        Card card = deck.getFirst();
        if (removeAfterGet) deck.removeFirst();
        return card;
    }
}
