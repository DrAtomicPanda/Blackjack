import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    // private int nrofDecks = 8;
    private ArrayList<Cards> deck;

    public Deck() {
        this.deck = newDeck();
    }

    ArrayList<Cards> deckCheck(ArrayList<Cards> mydeck) { //a way to make a new deck if about to deal the last card
        if (mydeck.size() <= 1) {
            this.deck = newDeck();
        }
        return this.deck;
    }

    public ArrayList<Cards> newDeck() {
        ArrayList<Cards> playable = new ArrayList<Cards>();
        for (int i = 0; i < 1; i++) {
            for (Cards s : Cards.values()) {
                playable.add(s); // https://www.youtube.com/watch?v=wq9SJb8VeyM A video on enums to help me fill my array
            }

        }
        for (int i = 0; i < 7; i++) {// Proper ammount to shuffle a deck
            Collections.shuffle(playable, new Random(2));
        } // https://www.geeksforgeeks.org/shuffle-or-randomize-a-list-in-java/# 
        return playable;
    }

    public Cards deal() {//A method deal from the deck
        Cards yourCard = this.deck.getLast();
        deckCheck(this.deck);
        this.deck.removeLast();
        return yourCard;

    }

    public static int getValue(ArrayList<Cards> Hands) {//my way to add up all the cards
        int x = 0;
        int a = 0;
        for (Cards s : Hands) {
            x = x + s.getValue();
        }
        for (Cards s : Hands) {//counts the number of aces
            if (s == Cards.Ace_of_Clubs || s == Cards.Ace_of_Spades
                    || s == Cards.Ace_of_Hearts || s == Cards.Ace_of_Diamonds) {
                a++;
            }
        }
        if (x > 21 && a > 0) {//turns the aces into ones if exceeding 21
            x = x - a * 10;
        }
        return x;
    }

    public String toString() {
        return " Current deck: " + deck;
    }
}

// public static void main(String[] args) {
// // System.out.println(newDeck());
// // ArrayList<Cards> playable = newDeck();
// // System.out.println(pullfromDeck(playable));
// System.out.println(newDeck());
// }
// public static Cards pullfromDeck(ArrayList<Cards> playable) {
// Cards yourCard = playable.getLast();
// playable = BlackJack.deckCheck(playable);
// playable.removeLast();
// return yourCard;

// }