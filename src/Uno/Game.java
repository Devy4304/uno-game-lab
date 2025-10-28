package Uno;

public class Game {
    public static Deck drawPile = new Deck(false);
    public static Deck discardPile = new Deck(true);

    public static Player[] players;

    private static int currentPlayer = 0;

    private static int flowDirection = 1;

    /**
     * Initializes the game by setting up the draw pile, discard pile, and players.
     * The draw pile is reset, a starting card is placed on the discard pile, and
     * the players are created. At least one human player is assumed, with additional
     * computer-controlled players based on the specified number of bots.
     *
     * @param numBots the number of computer-controlled players to include in the game
     */
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

    /**
     * Retrieves the current flow direction of the game.
     * The flow direction determines the order in which players take turns.
     * A positive value (e.g., 1) indicates clockwise flow, while a negative value (e.g., -1)
     * denotes counterclockwise flow.
     *
     * @return the current flow direction as an integer, where 1 represents clockwise,
     *         and -1 represents counterclockwise.
     */
    public static int getFlowDirection() {
        return flowDirection;
    }

    /**
     * Reverses the current flow direction of the game.
     * The flow direction determines the order in which players take turns. A value of 1 represents
     * clockwise flow, while a value of -1 represents counterclockwise flow. This method toggles
     * the flow direction between these two states.
     */
    public static void flipFlowDirection() {
        flowDirection = (flowDirection == 1) ? -1 : 1;
    }

    public static void addCardsToNextPlayer(boolean fourCards) {
        Hand nextPlayersHand = players[getNextPlayer()].getHand();
        for (int i = 0; i < ((fourCards) ? 4 : 2); i++) {
            nextPlayersHand.addCard(Game.drawPile.getTopCard(true));
        }
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void advancePlayer() {
        currentPlayer = getNextPlayer();
    }

    private static int getNextPlayer() {
        int player = currentPlayer + flowDirection + players.length; // advance the player pointer and will guarantee it is not less than 0
        return (player % players.length); // make it stay within the upper bounds
    }

    public static void skipPlayer() {
        currentPlayer += (flowDirection * 2) + (players.length * 2); // advance the player pointer twice and guarantee it is not less than 0
        currentPlayer %= players.length; // make it stay within the upper bounds
    }

    /**
     * Prints a summary of all bot players' game statuses.
     * <p>
     * This method iterates through all players in the game starting from the second player,
     * since the first player is assumed to be the human player. For each bot player, the
     * following details are included in the summary:
     * - The bot's username
     * - The most recent card the bot has played
     * - The total number of cards remaining in the bot's hand
     * <p>
     * The information is formatted, separated by dividing lines, and displayed using
     * a utility method for rendering a text-based user interface box.
     */
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
