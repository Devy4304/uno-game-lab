package Uno;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(3);
        for (int i = 0; i < Game.players.length; i++) {
            Utility.Console.writeTUIBox("Current Card: " + Game.discardPile.getTopCard(false) +
                    ";Current Player: " + Game.players[i].username +
                    ";" + Game.players[i].hand.toString(),
                    30, (i != 0), (i != 3)
            );
        }
    }
}
