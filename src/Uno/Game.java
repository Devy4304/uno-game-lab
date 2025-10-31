package Uno;

public class Game {
    /**
     * Represents the draw pile of the game, which serves as the primary deck of cards
     * used during gameplay. Players draw cards from this pile during their turns
     * or as part of game actions.
     * <p>
     * The draw pile is initialized with a shuffled deck that does not include duplicates
     * or other special configurations unless explicitly defined in game setup.
     * <p>
     * The draw pile is a shared resource for all players and plays a critical role in
     * maintaining the flow of the game.
     */
    public static final Deck drawPile = new Deck(false);

    /**
     * Represents the discard pile in the card game.
     * <p>
     * This static field holds the deck of cards designated as the discard pile.
     * The discard pile is initialized with an empty state, allowing cards to be
     * added during gameplay as players play their turns.
     * <p>
     * The discard pile serves as the central location for cards discarded by
     * players, with the topmost card on this pile determining certain game
     * rules or moves.
     */
    public static final Deck discardPile = new Deck(true);

    /**
     * An array representing all the players in the game.
     * <p>
     * This array holds instances of the `Player` class, which represent
     * both human and computer-controlled players involved in the game.
     * The first player in the array is typically the human player, and
     * later entries represent computer-controlled players (bots).
     * <p>
     * The number of elements in this array is determined during game
     * initialization and includes one human player alongside a configurable
     * number of bots. The order of players in the array reflects the turn
     * order during gameplay.
     */
    public static Player[] players;

    private static int currentPlayer = 0;

    private static int flowDirection = 1;

    private static boolean isGameOver = false;

    private static int winningPlayer;

    /**
     * Initializes the game by setting up the draw pile, discard pile, and players.
     * The draw pile is reset, a starting card is placed on the discard pile, and
     * the players are created. At least one human player is assumed, with additional
     * computer-controlled players based on the specified number of bots.
     *
     * @param numBots the number of computer-controlled players to include in the game
     */
    public static void initGame(int numBots, String playerUsername) {
        drawPile.resetDeck();

        discardPile.addCardToPile(drawPile.getTopCard(true));
        discardPile.prepDiscardPile();

        players = new Player[numBots + 1];
        // players[0] = new Player(Utility.Console.askForUsername());
        players[0] = new Player(playerUsername);
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

    /**
     * Adds a specified number of cards to the next player's hand from the draw pile.
     * The number of cards added depends on the given boolean parameter.
     * If {@code fourCards} is true, four cards will be added; otherwise, two cards will be added.
     *
     * @param fourCards a boolean value indicating whether to add four cards (true) or two cards (false)
     */
    public static void addCardsToNextPlayer(boolean fourCards) {
        Hand nextPlayersHand = players[getNextPlayer()].getHand();
        for (int i = 0; i < ((fourCards) ? 4 : 2); i++) {
            nextPlayersHand.addCard(Game.drawPile.getTopCard(true));
        }
    }

    /**
     * Retrieves the index of the current player whose turn it is in the game.
     *
     * @return the index of the current player as an integer.
     */
    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Advances turn to the next player in the game.
     * <p>
     * This method updates the `currentPlayer` field to reflect the next player's
     * turn based on the current flow direction and player order. The next player
     * is calculated using the private `getNextPlayer` method, which ensures the
     * appropriate player index is determined, wrapping around if necessary.
     */
    public static void advancePlayer() {
        currentPlayer = getNextPlayer();
    }

    /**
     * Calculates and returns the index of the next player in the game.
     * This method ensures proper player rotation, considering the game's
     * flow direction and the total number of players. The calculation
     * wraps around to stay within the valid range of player indices.
     *
     * @return the index of the next player as an integer.
     */
    private static int getNextPlayer() {
        int player = currentPlayer + flowDirection + players.length; // advance the player pointer and will guarantee it is not less than 0
        return (player % players.length); // make it stay within the upper bounds
    }

    /**
     * Skips the current player's turn in the game.
     * <p>
     * This method advances the player pointer by two positions, effectively skipping
     * the next player. It adjusts the `currentPlayer` index based on the total number
     * of players and the game's flow direction to ensure proper wrapping within bounds.
     * The resulting player index is calculated using modular arithmetic to guarantee
     * it remains within the range of valid player indices.
     */
    public static void skipPlayer() {
        currentPlayer += (flowDirection) + (players.length); // advance the player pointer twice and guarantee it is not less than 0
        currentPlayer %= players.length; // make it stay within the upper bounds
    }

    /**
     * Determines whether it is currently the human player's turn.
     * The first player (index 0) is assumed to be the human player.
     *
     * @return true if it is the human player's turn; false otherwise.
     */
    public static boolean isPlayerTurn() {
        return (currentPlayer == 0);
    }

    public static void endGame() {
        isGameOver = true;
        winningPlayer = currentPlayer;
    }

    public static int getWinningPlayer() {
        return winningPlayer;
    }

    /**
     * Determines whether the game is currently over.
     *
     * @return true if the game is over; false otherwise.
     */
    public static boolean isGameOver() {
        return isGameOver;
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
