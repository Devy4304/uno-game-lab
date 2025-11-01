package Uno;

public class Main {
    public static void main(String[] args) {
        Utility.Console.checkForANSI();
        Utility.Console.writeTUIBox("What is your username?", false, false);
        Game.initGame(3, Utility.Console.getStringInput());

        while (!Game.isGameOver()) {
            Game.players[0].queryUserAction();
            while (!Game.isPlayerTurn()) {
                int currentPlayer = Game.getCurrentPlayer();
                Game.players[currentPlayer].makeBotMove();
                int numCards = Game.players[currentPlayer].getHand().numCardsInHand();
                Utility.Console.writeTUIBox(Game.players[currentPlayer].getUsername() + ((Game.players[currentPlayer].getLatestPlayedCard().equals("drew a card")) ? " " : " played a ") + Game.players[currentPlayer].getLatestPlayedCard() +
                        ".;They now have " + numCards + " card(s) left." + ((numCards == 1) ? ";Uno!" : ""),
                        false, false);
                if (Game.isGameOver()) {
                    break;
                }
            }
        }
        Utility.Console.writeTUIBox(Game.players[Game.getWinningPlayer()].getUsername() + " won the game!", false, false);
    }
}