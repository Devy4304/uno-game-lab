package Uno;

public class Game {
    public static Deck drawPile = new Deck(false);
    public static Deck discardPile = new Deck(true);

    public static Player[] players;

    public static void initGame(int numBots) {
        drawPile.resetDeck();

        discardPile.addCardToPile(drawPile.getTopCard(true));
        discardPile.prepDiscardPile();

        players = new Player[numBots + 1];
        // players[0] = new Player(Utility.Console.askForUsername());
        players[0] = new Player("e");
        for (int i = 1; i <= numBots; i++) {
            players[i] = new Player("Bot " + i, true);
        }
    }
}
