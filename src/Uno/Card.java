package Uno;

public class Card {
    public enum Colors {
        RED,
        YELLOW,
        GREEN,
        BLUE
    }

    private final int cardNum;
    /*
    0-9: Numbers
    10: Draw Two
    11: Reverse
    12: Skip
    13: Wild
    14: Draw 4 Wild
     */
    private final Colors cardColor;

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
     * Constructs a text representation of the card, including its color and proper name.
     * The color is derived from the card's color using the color code provided by the `getColorCode` method.
     * The proper name of the card is determined by the `getProperName` method.
     * The resulting string combines the color code, the name of the card, and a reset code to terminate formatting.
     *
     * @return a string containing the color-coded name of the card.
     */
    public String getColoredCardText() {
        return getColorCode(cardColor) + getProperName() + Utility.RESET;
    }

    /**
     * Returns the color code string associated with the specified color from the Colors enum.
     * If the provided color does not match any defined case, a reset color code is returned.
     *
     * @param color the color from the Colors enum for which the color code is requested
     * @return the corresponding ANSI color code string for the specified color
     */
    public static String getColorCode(Colors color) {
        switch (color) {
            case Colors.RED -> {
                return Utility.RED;
            }
            case Colors.YELLOW -> {
                return Utility.YELLOW;
            }
            case Colors.GREEN -> {
                return Utility.GREEN;
            }
            case Colors.BLUE -> {
                return Utility.BLUE;
            }default -> {
                return Utility.RESET;
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
        return (cardNum == otherCard.cardNum || cardColor == otherCard.cardColor);
    }

    /**
     * Returns a string representation of the card, including its color and proper name.
     * The color is represented as a string derived from the card's `Colors` enum,
     * and the proper name is determined by the card's number using `getProperName`.
     *
     * @return a string containing the card's color and proper name separated by a space.
     */
    public String toString() {
        return cardColor + " " + getProperName();
    }
}
