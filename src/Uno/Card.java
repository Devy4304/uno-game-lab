package Uno;

/**
 * Represents a card used in the game of Uno.
 * Each card has a numerical identifier and a color. Cards with special
 * numbers correspond to unique actions in the game, such as "Draw Two."
 * Wild cards can change their color during gameplay.
 */
public class Card {
    /**
     * The Colors enum represents the possible colors of a card in a card game.
     * It includes the primary colors used to categorize cards.
     * This enum is primarily utilized to define and handle the colors of game cards,
     * offering a standardized set of values for color comparison and management.
     */
    public enum Colors {
        RED,
        YELLOW,
        GREEN,
        BLUE
    }

    /**
     * Represents the numerical identifier of the card. The card number determines
     * the type or value of the card. Valid values range from 0 to 14:
     * - 0 through 9 are numeric cards
     * - 10 represents "Draw Two"
     * - 11 represents "Reverse"
     * - 12 represents "Skip"
     * - 13 represents "Wild"
     * - 14 represents "Wild Draw 4"
     * <p>
     * This value is immutable once the card has been created.
     */
    private final int cardNum;

    /**
     * Represents the color of a card in the Uno game.
     * Derived from the Colors enum, which includes RED, YELLOW, GREEN, and BLUE.
     * This variable is primarily used to indicate and handle the card's associated color.
     */
    private Colors cardColor;

    /**
     * Constructs a Uno.Card with a specified number and color.
     *
     * @param cardNumber the numerical identifier of the card which determines its type
     *                   (valid values range from 0 to 14: 0-9 for numbers, 10 for Draw Two,
     *                   11 for Reverse, 12 for Skip, 13 for Wild, and 14 for Wild Draw 4)
     * @param cardColor  the color of the card from the Colors enum (RED, YELLOW, GREEN, or BLUE)
     */
    public Card(int cardNumber, Colors cardColor) {
        this.cardNum = cardNumber;
        this.cardColor = cardColor;
    }

    /**
     * Compares this card with another card for equality.
     * Two cards are considered equal if they have the same numerical identifier and color.
     *
     * @param otherCard the card to compare with the current card
     * @return true if the cards have the same number and color, otherwise false
     */
    public boolean equals(Card otherCard) {
        return (cardNum == otherCard.cardNum && cardColor == otherCard.cardColor);
    }

    /**
     * Retrieves the color of the card.
     * The color is represented as a value from the Colors enum,
     * which includes RED, YELLOW, GREEN, and BLUE.
     *
     * @return the color of the card as a Colors enum value.
     */
    public Colors getCardColor() {
        return cardColor;
    }

    /**
     * Retrieves the numerical identifier of the card.
     * The card number determines the type or value of the card,
     * with numbers ranging from 0-14 representing various card types.
     *
     * @return the numerical identifier of the card.
     */
    public int getCardNum() {
        return cardNum;
    }

    /**
     * Provides the proper name of the card based on its number.
     * Numbers 0-9 will return their string representation, while special
     * cards like "Draw Two", "Reverse", "Skip", "Wild", and "Wild Draw 4"
     * will return their corresponding names. If the card number is invalid,
     * "invalid" will be returned.
     *
     * @return the proper name of the card as a string, or "invalid" if the number is out of range.
     */
    public String getProperName() {
        if (cardNum <= 9) {
            return String.valueOf(cardNum);
        } else if (cardNum == 10) {
            return "Draw Two";
        } else if (cardNum == 11) {
            return "Reverse";
        } else if (cardNum == 12) {
            return "Skip";
        } else if (cardNum == 13) {
            return "Wild";
        } else if (cardNum == 14) {
            return "Wild Draw 4";
        } else {
            return "invalid";
        }
    }

    /**
     * Sets the color for a wild card, allowing it to change its color during gameplay.
     * This operation is only permitted if the card number represents a wild card (13 or higher).
     *
     * @param color the new color to assign to the wild card, represented as a value from the Colors enum
     */
    public void setWildColor(Colors color) {
        if (cardNum >= 13) {
            cardColor = color;
        }
    }

    /**
     * Generates and returns a textual representation of the card with optional color formatting.
     * If the card is a special wild card (cardNum >= 13) and the `showWildColor` parameter is false,
     * the card text is returned without the color formatting. For other cards or when `showWildColor`
     * is true, the color code and card color are included in the output.
     *
     * @param showWildColor a boolean indicating whether to include color formatting for wild cards.
     *                      If true, the color formatting is applied even for wild cards. If false,
     *                      wild cards are rendered without a color prefix.
     * @return a string representing the card's color and name, with optional color formatting based
     *         on the `showWildColor` parameter.
     */
    public String getColoredCardText(boolean showWildColor) {
        if (cardNum < 13 || showWildColor) {
            return getColorCode(cardColor) + cardColor + " " + getProperName() + Utility.Console.Colors.RESET;
        } else {
            return Utility.Console.Colors.RESET + getProperName() + Utility.Console.Colors.RESET;
        }
    }

    /**
     * Returns the console color code corresponding to the specified color.
     * If the provided color does not match any specific case, it defaults to the reset color code.
     *
     * @param color the color provided as a value from the Colors enum
     *              (RED, YELLOW, GREEN, or BLUE).
     * @return the console color code as a string corresponding to the provided color.
     */
    public static String getColorCode(Colors color) {
        switch (color) {
            case Colors.RED -> {
                return Utility.Console.Colors.RED;
            }
            case Colors.YELLOW -> {
                return Utility.Console.Colors.YELLOW;
            }
            case Colors.GREEN -> {
                return Utility.Console.Colors.GREEN;
            }
            case Colors.BLUE -> {
                return Utility.Console.Colors.BLUE;
            }default -> {
                return Utility.Console.Colors.RESET;
            }
        }
    }

    /**
     * Determines if the current card can be played on top of another card.
     * A card is playable if it has the same number or the same color as the provided card.
     *
     * @param otherCard the card to compare against for playability
     * @return true if the current card can be played on the provided card; otherwise, false
     */
    public boolean canPlayCard(Card otherCard) {
        return (cardNum == otherCard.cardNum || cardColor == otherCard.cardColor || cardNum >= 13);
    }

    /**
     * Returns the string representation of the card.
     * This representation includes the card's color and name without additional
     * color formatting for wild cards.
     *
     * @return a string representing the card's color and name without color formatting for wild cards.
     */
    public String toString() {
        return getColoredCardText(false);
    }
}
