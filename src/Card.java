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

    public Card(int cardNumber, Colors cardColor) {
        this.cardNum = cardNumber;
        this.cardColor = cardColor;
    }

    public boolean equals(Card otherCard) {
        return (cardNum == otherCard.cardNum && cardColor == otherCard.cardColor);
    }
    public Colors getCardColor() {
        return cardColor;
    }

    public int getCardNum() {
        return cardNum;
    }

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

    public String toString() {
        return cardColor + " " + getProperName();
    }
}
