package Uno;

public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck(false);
        Hand hand = new Hand(deck);

        System.out.println("Test");
        Utility.Console.clear();
        Utility.Console.writeTUIBox(Utility.Console.Colors.YELLOW + "UNO" + Utility.Console.Colors.RESET + " the game!", 30, true, false);
        Utility.Console.writeTUIBox(hand.toString(), 30, false, true);
        Utility.Console.writeTUIBox(Utility.Console.Colors.PURPLE + "This is a test TUI box", 30, false, false);
    }
}
