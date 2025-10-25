package Uno;

public class Main {
    public static void main(String[] args) {
        Utility.Console.checkForANSI();
        Game.initGame(3);
        for (int i = 0; i < 4; i++) {
            for (int x = 1; x <= 3; x++) {
                Utility.Console.writeTUIBox(
                        "Current Card: " + Game.discardPile.getTopCard(false).getColoredCardText(true) + ";" +
                        "Current Player: " + Game.players[x].getUsername() + ";" +
                        Game.players[x].getHand().toString(),
                        false, false);
                Game.players[x].makeBotMove();
            }
        }
    }
}
