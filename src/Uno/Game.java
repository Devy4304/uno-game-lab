package Uno;

public class Game {
    public static Deck drawPile = new Deck(false);
    public static Deck discardPile = new Deck(true);

    public static Player[] players;

    private static int flowDirection = 1;

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

    public static int getFlowDirection() {
        return flowDirection;
    }

    public static void flipFlowDirection() {
        flowDirection = (flowDirection == 1) ? -1 : 1;
    }

    public static void printBotSummary() {
        StringBuilder text = new StringBuilder();
        for (int i = 1; i < players.length; i++) {
            if (i != 1) text.append("-".repeat(Utility.Console.getBoxWidth() + 2)).append(";");
            text.append(players[i].getUsername());
            text.append(";Card Played: ").append(players[i].getLatestPlayedCard());
            text.append(";Total Cards Remaining: ").append(players[i].getHand().numCardsInHand()).append(";");
        }
        Utility.Console.writeTUIBox(String.valueOf(text), false, false);
    }
}
