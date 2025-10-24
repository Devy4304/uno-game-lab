package Uno;

public class Main {
    public static void main(String[] args) {
        Utility.Console.checkForANSI();

        Game game = new Game(3);
        Game.discardPile.prepDiscardPile();
        for (int i = 0; i < Game.players.length; i++) {
            Utility.Console.writeTUIBox("Current Card: " + Game.discardPile.getTopCard(false) +
                    ";Current Player: " + Game.players[i].username +
                    ";" + Game.players[i].hand.toString(),
                    (i != 3),(i != 0)
            );
        }
    }
}
