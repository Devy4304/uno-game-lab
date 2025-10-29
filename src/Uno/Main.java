package Uno;

public class Main {
    public static void main(String[] args) {
        Utility.Console.checkForANSI();
        Game.initGame(3);

        while (!Game.isGameOver()) {
            Game.players[0].queryUserAction();
            while (!Game.isPlayerTurn()) {
                int currentPlayer = Game.getCurrentPlayer();
                Game.players[currentPlayer].makeBotMove();
                Utility.Console.writeTUIBox(Game.players[currentPlayer].getUsername() + " played a " + Game.players[currentPlayer].getLatestPlayedCard() +
                        "They now have " + Game.players[currentPlayer].getHand().numCardsInHand() + " card(s) left in their hand.",
                        false, false);
                // Utility.Console.writeTUIBox(
                //         "Current Player: " + Game.players[Game.getCurrentPlayer()].getUsername() + ";" +
                //                 "Current Card: " + Game.discardPile.getTopCard(false).getColoredCardText(true) + ";" +
                //                 "Number of Cards: " + Game.players[Game.getCurrentPlayer()].getHand().numCardsInHand() + ";" +
                //                 Game.players[Game.getCurrentPlayer()].getHand().toString(),
                //         false, false);
            }
        }
    }
}
