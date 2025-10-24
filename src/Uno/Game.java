package Uno;

public class Game {
    public static Deck drawPile = new Deck(true);
    public static Deck discardPile = new Deck(false);


    public static Player[] players;

    public Game(int numBots) {
        players = new Player[numBots + 1];
        players[0] = new Player(Utility.Console.askForUsername());
        for (int i = 1; i <= numBots; i++) {
            players[i] = new Bot("Bot " + i);
        }
        discardPile.addCardToPile(drawPile.getTopCard(true));
    }
}
