package Uno;

public class Main {
    public static void main(String[] args) {
        // Utility.Console.checkForANSI();
        Game.initGame(3);
        Utility.Console.askForWildColor();
        for (int i = 0; i < 8; i++) {
            for (int x = 1; x <= 3; x++) {
                String text = "Current Player: " + Game.players[x].getUsername() + ";Current Card: " + Game.discardPile.getTopCard(false).getColoredCardText(true) + ";" + Game.players[x].getHand().toString();
                Utility.Console.writeTUIBox(text, false, false);
                Game.players[x].makeBotMove();
            }
        }
    }
}
