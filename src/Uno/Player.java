package Uno;

public class Player {
    Hand hand;
    String username;

    public Player(String username) {
        hand = new Hand();
        this.username = username.replaceAll("[^a-zA-Z0-9 _]","");
    }

    // public void playCard(int cardHandPlayableIndex) {
    //
    // }
}
