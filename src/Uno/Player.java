package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private final Hand hand;
    private final String username;
    private final boolean bot;
    private Card latestPlayedCard;
    private boolean lastActionWasDraw;

    /**
     * Constructs a new Player object with a specified username and bot status.
     * The username will automatically strip all characters except alphanumeric,
     * space, and underscore to ensure validity.
     *
     * @param username the username of the player
     * @param isBot a boolean flag indicating whether the player is a bot
     */
    public Player(String username, boolean isBot) {
        this.bot = isBot;
        hand = new Hand();
        this.username = username.replaceAll("[^a-zA-Z0-9 _]","");
    }

    /**
     * Constructs a new Player object with a specified username.
     * The username will automatically strip all characters except alphanumeric, space,
     * and underscore to ensure validity. By default, the created player is not a bot.
     *
     * @param username the username of the player
     */
    public Player(String username) {
        hand = new Hand();
        this.username = username.replaceAll("[^a-zA-Z0-9 _]","");
        this.bot = false; // default human
    }

    /**
     * Determines if the player is a bot.
     *
     * @return true if the player is a bot, otherwise false.
     */
    public boolean isBot() {
        return bot;
    }

    /**
     * Retrieves the username of the player.
     *
     * @return the username of the player as a string.
     */

    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the hand of the player.
     *
     * @return the Hand object representing the player's current hand of cards.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Determines if the player has won the game.
     * A player is considered to have won if their hand contains no cards.
     *
     * @return true if the player has no cards in their hand, otherwise false.
     */
    public boolean isWin() {
        return (hand.numCardsInHand() == 0);
    }

    /**
     * Performs an action in the game based on the provided card index.
     * The action varies depending on whether the card index corresponds to
     * a card in the player's hand, an intent to draw a card, or an invalid input.
     * <p>
     * If the card index is valid and within the range of the player's hand, the
     * corresponding card is played from the hand and added to the discard pile.
     * If the index corresponds to drawing a card, a card is taken from the draw pile
     * and added to the player's hand. Invalid indices throw an error.
     *
     * @param cardHandIndex the index of the card the player wants to play from their hand,
     *                      or the index representing the action to draw a card
     *                      (equal to the number of cards in the hand plus one).
     *                      Indices outside this range are considered invalid.
     *
     * @throws Error if the cardHandIndex is invalid.
     */
    public void action(int cardHandIndex) {
        if (cardHandIndex >= 0 && cardHandIndex < hand.numCardsInHand()) {
            Card card = hand.getCardFromHand(cardHandIndex, true);
            Game.discardPile.addCardToPile(card);
            specialCardAction(card);
            latestPlayedCard = card;
            lastActionWasDraw = false;
        } else if (cardHandIndex == hand.numCardsInHand() + 1) {
            hand.addCard(Game.drawPile.getTopCard(true));
            latestPlayedCard = null;
            lastActionWasDraw = true;
        } else {
            // Worry about reprompting later
            throw new Error("You typed an invalid value!" + cardHandIndex);
        }
    }

    private void specialCardAction(Card card) {
        int cardNum = card.getCardNum();

    }

    // --------------------------- BOT CODE -------------------------------------------

    /**
     * Executes the bot's move during its turn in the game.
     * The method determines the optimal action for the bot based on card scores,
     * either playing the best available card or drawing a new card from the draw pile.
     * <p>
     * If there are card(s) in the bot's hand that can be played, the card with the
     * highest score is selected. If the selected card is a wild card, its color
     * is set to the most frequent color in the bot's hand before playing it.
     * If no playable cards are available, the bot draws a card instead.
     * <p>
     * Logic:
     * 1. Calculate the scores for each card in the bot's hand.
     * 2. Identify the card with the highest score.
     * 3. If no playable cards are found (the best score is 0), draw a card from the
     *    draw pile and end the turn.
     * 4. If the best card is a wild card, set its color to maximize strategic advantage.
     * 5. Play the card with the highest score.
     * <p>
     * Updates:
     * - Modifies the bot's hand by either adding a card (in case of drawing)
     *   or removing the played card.
     * - Sets the `latestPlayedCard` field with the card played or null if a card was drawn.
     * - Updates the `lastActionWasDraw` field to reflect whether the bot's last action was drawing a card.
     */
    public void makeBotMove() {
        List<Integer> scores = calculateCardScores();

        int bestScore = 0;

        for (int score : scores) {
            if (score > bestScore) bestScore = score;
        }

        if (bestScore == 0) {
            hand.addCard(Game.drawPile.getTopCard(true));
            lastActionWasDraw = true;
            latestPlayedCard = null;
            return;
        }

        if (hand.getCardFromHand(scores.indexOf(bestScore), false).getCardNum() >= 13) {
            hand.getCardFromHand(scores.indexOf(bestScore), false).setWildColor(hand.getColorThatTheMostCardsUse());
        }

        action(scores.indexOf(bestScore));
    }

    /**
     * Calculates the scores of cards in the player's hand to determine their playability.
     * The scoring mechanism varies based on whether the player is a bot or not.
     * <p>
     * If the player is a bot, it computes the scores based on the bot's playable cards
     * and the current card on the deck. If the player is not a bot, a default score of 0
     * is assigned to all cards in the hand.
     *
     * @return a list of integers representing the scores for each card in the player's hand.
     *         For a bot, the scores reflect playability and game logic.
     *         For a non-bot player, the scores are all set to 0.
     */
    public List<Integer> calculateCardScores() {
        if (bot) {
            Card currentCardOnDeck = Game.discardPile.getTopCard(false);
            List<Integer> playableCards = hand.getPlayableCards();
            return getScores(playableCards, currentCardOnDeck);
        }
        return new ArrayList<>(Collections.nCopies(hand.getHand().size(), 0));
    }

    /**
     * Calculates the scores of playable cards in the player's hand based on game rules and
     * the current card on the deck.
     *
     * @param playableCards a list of integers representing the indices of cards in the player's
     *                      hand that are playable according to the game rules.
     * @param currentCardOnDeck the card currently on the top of the deck, used to compare
     *                          properties and calculate scores.
     * @return a list of integers representing the calculated scores for each card in the
     *         player's hand. Scores are ordered according to the indices of the cards in the hand.
     */
    private List<Integer> getScores(List<Integer> playableCards, Card currentCardOnDeck) {
        List<Integer> scores = new ArrayList<>(Collections.nCopies(hand.getHand().size(), 0));

        for (int index : playableCards) {
            int cardNum = hand.getHand().get(index).getCardNum();
            Card.Colors cardColor = hand.getHand().get(index).getCardColor();

            int score = 5;
            score += (cardNum > 9) ? 20 : 10;
            score += (cardNum >= 13) ? -10 : 5;
            score += (cardColor == currentCardOnDeck.getCardColor()) ? 8 : 0;
            score += (cardNum == currentCardOnDeck.getCardNum()) ? 6 : 0;

            scores.set(index, score);
        }
        return scores;
    }

    /**
     * Retrieves a description of the most recent action involving a card.
     * If the last action was drawing a card, it returns an appropriate description.
     * Otherwise, it provides the color-coded text representation of the latest played card,
     * or a message indicating that no card has been played yet.
     *
     * @return a string describing the last action or the latest played card.
     *         Returns "Drew a card" if the last action was drawing a card.
     *         Returns the color-coded text of the latest played card if one exists.
     *         Returns "No card played yet" if no card has been played.
     */
    public String getLatestPlayedCard() {
        if (lastActionWasDraw) return "Drew a card";
        return (latestPlayedCard != null) ? latestPlayedCard.getColoredCardText(true) : "No card played yet";
    }
}
