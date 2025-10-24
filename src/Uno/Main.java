package Uno;

public class Main {
    public static void main(String[] args) {
        Utility.Console.checkForANSI();

        Game game = new Game(3);
        Game.discardPile.prepDiscardPile();


    }
}
