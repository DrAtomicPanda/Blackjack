import java.util.*;
import java.io.*;

public class BlackJack {
    public static int money;// list of all my variables
    public static int cash;
    public static int splitcount;
    public static int bet;
    public static int newbet;
    public Deck myDeck = new Deck();// gives everything access to my deck

    public static void main(String[] args) throws FileNotFoundException {
        start();// a method to take the bulk out of the main method
        if (bet <= 0) {// checks if you have enough to bet
            System.out.println("You just trying to play for fun?\nSorry there must be stakes");
        } else {
            newbet = 0;
            play();
        }
        File Money = new File("money.txt");
        Scanner file = new Scanner(Money);
        PrintStream dollars = new PrintStream(Money);
        dollars.print(cash);
        dollars.close();
        file.close();
    }

    public static void start() throws FileNotFoundException {
        File Money = new File("money.txt");
        Scanner file = new Scanner(Money);
        Scanner console = new Scanner(System.in);
        System.out.println(
                "Welcome to BlackJack \nWould you like to play a New Game or Continue from Last Save?\nPlease enter either Continue or New Game?");
        String asd = console.nextLine();
        boolean correct = false;
        while (!correct) {
            switch (asd.toLowerCase()) {
                case "continue":
                    money = file.nextInt();
                    correct = true;
                    break;
                case "new game":
                    PrintStream dollars = new PrintStream(Money);
                    dollars.print(500);
                    money = file.nextInt();
                    dollars.close();
                    correct = true;
                    break;
                default:
                    System.out.print("Please enter \"Continue\" or \"New Game\"\n");
                    asd = console.nextLine();
            }
        }
        cash = money;
        Clear.clearScreen();
        System.out.println("Starting cash: " + money);
        System.out.println("How much would you like to bet");
        while (!console.hasNextInt()) {
            console.nextLine();
            System.out.println("Please enter a valid integer");
        }
        bet = console.nextInt();
        if (bet > cash) {
            while (bet > cash) {
                System.out.println("Please enter a value in with the money you have");
                while (!console.hasNextInt()) {
                    console.nextLine();
                    System.out.println("Please enter a valid integer");
                }
                bet = console.nextInt();
            }
        }
    }

    public static void play()throws FileNotFoundException {
        ArrayList<Cards> dealer = new ArrayList<>();// all the arrays i need for the game
        ArrayList<Cards> Hand = new ArrayList<>();
        ArrayList<Cards> Hand1 = new ArrayList<Cards>();
        ArrayList<Cards> Hand2 = new ArrayList<Cards>();
        Scanner console = new Scanner(System.in);
        Deck myDeck = new Deck();
        while (cash > 0) {// keeps the game looping while you have money
            dealer.clear();
            Hand.clear();
            for (int i = 0; i < 2; i++) {
                Hand.add(myDeck.deal());
                dealer.add(myDeck.deal());
            }
            System.out.println("Cash left: " + cash);
            System.out.println("Dealers hand : [Hidden, " + dealer.getLast() + "]");
            System.out.println("Your hand: " + Hand + Deck.getValue(Hand));
            while (Deck.getValue(Hand) < 21) {
                System.out.println("Would you like to do?");
                String action = console.nextLine();
                while (!action.equalsIgnoreCase("stand")) {
                    switch (action.toLowerCase()) {
                        case "split":// allows you to split once making your current hand two playable hands
                            if (splitcount < 1 & Hand.getLast().getValue() == Hand.getFirst().getValue()
                                    & Hand.size() == 2) {
                                splitcount++;
                                Hand1.add(Hand.getFirst());
                                Hand1.add(myDeck.deal());
                                split(Hand1, dealer, myDeck);// another play method that takes parameters
                                Hand2.add(Hand.getLast());
                                Hand2.add(myDeck.deal());
                                Hand.clear();
                                for (Cards s : Hand2) {
                                    Hand.add(s);
                                }
                                Hand2.clear();
                                System.out.println("Your hand " + Hand + Deck.getValue(Hand));
                                action = console.nextLine();
                                break;
                            } else {
                                System.out.println(
                                        "Can only split once in this online casino or dont meet the conditions to split");
                                action = console.nextLine();
                                break;
                            }
                        case "double down":// allows you to hit once and double your bet
                            if (Hand.size() == 2 && cash > bet * 2) {
                                Hand.add(myDeck.deal());
                                newbet++;
                                action = "stand";
                                continue;
                            } else if (Hand.size() > 2) {
                                System.out.println("Cant double down if youve already hit");
                                action = console.nextLine();
                                continue;
                            } else {
                                System.out.println("Cant double down you are too poor");
                                action = console.nextLine();
                                continue;
                            }
                        case "hit":// allows you to "hit" and add another card to your hand
                            Hand.add(myDeck.deal());
                            System.out.println("Your hand " + Hand + Deck.getValue(Hand));
                            if (Deck.getValue(Hand) > 21) {
                                action = "stand";
                                break;
                            }
                            action = console.nextLine();
                            continue;
                        case "stand":// ends the addition of current hands to your deck
                            break;
                        case "save":
                            System.out.println("Cash remaining: " + cash);
                            return;
                        case "help":
                            System.out.println(
                                    "Your options are to \"Stand\" or \"Hit\" or \"Double Down\" or\n\"Save\"(which will save your progress) or\n\"new bet\"(will change how much you are betting)");
                            System.out.println("Your hand: " + Hand + Deck.getValue(Hand));
                            System.out.println("Would you like to do?");
                            action = console.nextLine();
                            break;
                        case "new bet":
                            System.out.println("Please enter your new wager here");
                            while (!console.hasNextInt()) {
                                System.out.println("Please enter a valid integer");
                                console.next();
                            }
                            bet = console.nextInt();
                            System.out.println("Your hand: " + Hand + Deck.getValue(Hand));
                            System.out.println("Would you like to do?");
                            action = console.nextLine();
                            break;
                        default:
                            System.out.println("If you dont know the commands or need help type \"help\"");
                            action = console.nextLine();
                    }
                    continue;
                }
                weWin(Hand, dealer, myDeck);
                if (splitcount > 0) {
                    weWin(Hand1, dealer, myDeck);
                    Hand1.clear();
                    splitcount = 0;
                }
                dealer.clear();
                Hand.clear();
                break;
            }
        }
        System.out.println("You broke thx for playing");
        console.close();
    }

    public static void weWin(ArrayList<Cards> yourHand, ArrayList<Cards> dealerHand, Deck currDeck) {
        if (Deck.getValue(yourHand) > 21) {
            cash = cash - bet;
            System.out.println("bust");
            System.out.println("Your hand " + yourHand + Deck.getValue(yourHand));
            if (newbet > 0) {
                cash = cash - bet;
                newbet--;
                System.out.println(newbet);
            }
            return;
        }
        while (Deck.getValue(yourHand) < 21 && Deck.getValue(dealerHand) < 17) {
            dealerHand.add(currDeck.deal());
        }
        if ((Deck.getValue(yourHand) <= 21 && Deck.getValue(dealerHand) > 21)
                || (Deck.getValue(yourHand) > Deck.getValue(dealerHand) && Deck.getValue(dealerHand) < 21)) {
            cash = cash + bet;
            if (newbet > 0) {
                cash = cash + bet;
                newbet--;
                System.out.println(newbet);
            }
        } else {
            cash = cash - bet;
            if (newbet > 0) {
                cash = cash - bet;
                newbet--;
                System.out.println(newbet);
            }
        }
        System.out.println("Dealers hand " + dealerHand + Deck.getValue(dealerHand));
        System.out.println("Your hand " + yourHand + Deck.getValue(yourHand));
    }

    public static void split(ArrayList<Cards> Hand, ArrayList<Cards> dealer, Deck myDeck) throws FileNotFoundException{
        Scanner console = new Scanner(System.in);
        System.out.println("Cash left: " + cash);
        System.out.println("Dealers hand : [Hidden, " + dealer.getLast() + "]");
        System.out.println("Your hand: " + Hand + Deck.getValue(Hand));
        while (Deck.getValue(Hand) < 21) {
            System.out.println("Would you like to do?");
            String action = console.nextLine();
            while (!action.equalsIgnoreCase("stand")) {
                switch (action.toLowerCase()) {
                    case "double down":
                        if (Hand.size() == 2 && cash > bet * 2) {
                            Hand.add(myDeck.deal());
                            newbet++;
                            action = "stand";
                            continue;
                        } else if (Hand.size() > 2) {
                            System.out.println("Cant double down if youve already hit");
                            action = console.nextLine();
                            continue;
                        } else {
                            System.out.println("Cant double down you are too poor");
                            action = console.nextLine();
                            continue;
                        }
                    case "hit":
                        Hand.add(myDeck.deal());
                        System.out.println("Your hand " + Hand + Deck.getValue(Hand));
                        if (Deck.getValue(Hand) > 21) {
                            action = "stand";
                            break;
                        }
                        action = console.nextLine();
                        continue;
                    case "stand":
                        break;
                    case "save":
                        System.out.println("Cash remaining: " + cash);
                        File Money = new File("money.txt");
                        PrintStream dollars = new PrintStream(Money);
                        dollars.print(cash);
                        console.close();
                        System.exit(0);
                        return;
                    case "help":
                        System.out.println(
                                "Your options are to \"Stand\" or \"Hit\" or \"Double Down\" or\n\"Save\"(which will save your progress) or\n\"new bet\"(will change how much you are betting)");
                        System.out.println("Your hand: " + Hand + Deck.getValue(Hand));
                        System.out.println("Would you like to do?");
                        action = console.nextLine();
                        break;
                    case "new bet":
                        System.out.println("Please enter your new wager here");
                        while (!console.hasNextInt()) {
                            System.out.println("Please enter a valid integer");
                            console.next();
                        }
                        bet = console.nextInt();
                        System.out.println("Your hand: " + Hand + Deck.getValue(Hand));
                        System.out.println("Would you like to do?");
                        action = console.nextLine();
                        break;
                    default:
                        System.out.println("If you dont know the commands or need help type \"help\"");
                        action = console.nextLine();
                }
            }
            return;
        }
    }
    // if (Deck.getValue(yourHand) == Deck.getValue(dealerHand)
    // || Deck.getValue(dealerHand) < Deck.getValue(dealerHand))
    // if (Deck.getValue(Hand) > 21) {
    // cash = cash - bet;
    // System.out.println("bust");
    // System.out.println("Your hand " + Hand + Deck.getValue(Hand));
    // if (newbet > 0) {
    // cash = cash - bet;
    // }
    // continue;
    // }
    // if (Deck.getValue(Hand) > 21) {
    // cash = cash - bet;
    // System.out.println("bust");
    // System.out.println("Your hand " + Hand + Deck.getValue(Hand));
    // if (newbet > 0) {
    // cash = cash - bet;
    // newbet = 0;
    // }
    // return;
    // }
    // weWin(Hand, dealer, myDeck);
}
// newbet = 0;
// dealer.clear();
// Hand.clear();

// ArrayList<ArrayList<Cards>> players = new ArrayList<>();
// for(int i = 0; i<2; i++){
// for(Hands h: players){
// h.add(myDeck.deal());
// }
// }
// yourDeck = deckCheck(yourDeck);
// ArrayList<Cards> Hand3 = new ArrayList<Cards>();
// ArrayList<Cards> Hand4 = new ArrayList<Cards>();
// if (Hand.getLast() != Hand.getFirst()) {
// System.out.println("Sorry you cant split unlike cards");
// action = console.nextLine();
// continue;
// }
// while (Deck.getValue(Hand1) < 21) {
// System.out.println("Would you like to do?");
// action = console.nextLine();
// playerHands.hithand(Hand1);
// }
// System.out.println(Hand2);
// return;
// public static void Split(ArrayList<Cards> hand1,ArrayList<Cards> hand2){

// if (Deck.getValue(hand1) > 21) {
// cash = cash - bet;
// System.out.println("bust");
// System.out.println("Your hand " + Hand + Deck.getValue(Hand));
// if (newbet > 0) {
// cash = cash - bet;
// }
// continue;
// }
// while (Deck.getValue(Hand) < 21 && Deck.getValue(dealer) < 17) {
// // yourDeck = deckCheck(yourDeck);
// dealer.add(myDeck.deal());
// }
// if ((Deck.getValue(Hand) <= 21 && Deck.getValue(dealer) > 21)
// || (Deck.getValue(Hand) > Deck.getValue(dealer) && Deck.getValue(dealer) <
// 21)) {
// cash = cash + bet;
// if (newbet > 0) {
// cash = cash + bet;
// }
// } else if (Deck.getValue(Hand) == Deck.getValue(dealer)
// || Deck.getValue(Hand) < Deck.getValue(dealer)) {
// cash = cash - bet;
// if (newbet > 0) {
// cash = cash - bet;
// }
// }
// }

// public static ArrayList<Cards> deckCheck(ArrayList<Cards> mydeck) {
// if (mydeck.size() <= 2 * (nrofplayers + 1)) {
// mydeck.clear();
// mydeck = Deck.newDeck();
// }
// return mydeck;
// }
// public static ArrayList<Cards> hit(ArrayList<Cards> test) {
// return test;
// }

// System.out.println(pullfromDeck());
// while(!console.hasNextInt()){
// bet = console.nextInt();
// if(console.hasNextInt()){
// bet = console.nextInt();
// }

// else{
// bet = 0;
// continue;
// }
// while(bet<=cash){
// for(int i =0; i<4; i++){
// ArrayList<Cards> dealer = new ArrayList<>();
// dealer.add(Deck.pullfromDeck(yourDeck));
// }
// }

// String action = console.nextLine();
// while (!action.equalsIgnoreCase("hit") && !action.equalsIgnoreCase("Stand")
// && !action.equalsIgnoreCase("end")) {
// System.out.print("Please enter \"Hit\" or \"Stand\"\n");
// action = console.nextLine();
// System.out.println("Your hand: " + Hand + Deck.getValue(Hand));
// }
// if (action.equalsIgnoreCase("hit")) {
// Hand.add(Deck.pullfromDeck(yourDeck));
// System.out.println(Hand+" "+ Deck.getValue(Hand));
// if(yourDeck.size()<=4){
// yourDeck = Deck.newDeck();
// }
// }
// if (action.equalsIgnoreCase("stand")) {
// break;
// }
// if (action.equalsIgnoreCase("end")) {
// return;
// }
// System.out.println("How much are you willing to bet?");
// while(console.hasNext()){
// try{
// bet = console.nextInt();
// }catch(InputMismatchException| NullPointerException IME ){
// bet = console.nextInt();
// }
// }
// bet = console.nextInt();
// while(console==null){
// try{
// bet = console.nextInt();
// }catch(InputMismatchException| NullPointerException IME ){
// bet = console.nextInt();
// }

// }
// String bug = "";
// do{
// System.out.println("How much are you willing to bet?");
// bug = console.nextLine();
// }while(!console.hasNextInt());
// bet = Integer.parseInt(bug);
// while (!asd.equalsIgnoreCase("continue") & !asd.equalsIgnoreCase("New Game"))
// {
// System.out.print("Please enter \"Continue\" or \"New Game\"\n");
// asd = console.nextLine();

// if (asd.equalsIgnoreCase("continue")) {
// money = file.nextInt();
// } else {
// PrintStream dollars = new PrintStream(Money);
// dollars.print(500);
// money = file.nextInt();
// dollars.close();

// }
// }
// yourDeck = deckCheck(yourDeck);
// System.out.print(yourDeck.size());
// dealer = dealerHand.getHand(yourDeck);
// yourDeck = dealerHand.remove(yourDeck);
// yourDeck = deckCheck(yourDeck);
// Hands dealer = new Hands();
// Hands player = new Hands();
// ArrayList<ArrayList<Cards>> players = new ArrayList<>();
// players.add(Deck.deal())
// for(int i = 0; i<nrofplayers; i++){
// for(Hands a:){
// a.add(Deck.deal());
// }
// }
// int nrofplayers;
// Scanner console = new Scanner(System.in);
// System.out.println("Starting cash: " + money);
// System.out.println("How much would you like to bet");
// while (!console.hasNextInt()) {
// console.nextLine();
// System.out.println("Please enter a valid integer");
// }
// bet = console.nextInt();
// if (bet > cash) {
// while (bet > cash) {
// System.out.println("Please enter a value in with the money you have");
// while (!console.hasNextInt()) {

// console.nextLine();
// System.out.println("Please enter a valid integer");
// }
// bet = console.nextInt();
// }
// }
// if (bet <= 0) {
// System.out.println("You just trying to play for fun?\nSorry there must be
// stakes");
// console.close();
// return;
// }
// System.out.println("How many players would you like to play against?");
// while (!console.hasNextInt()) {
// console.nextLine();
// System.out.println("Please enter a valid integer no more than 2 extra
// including yourself
// players");
// }
// nrofplayers = console.nextInt();
// console.nextLine();
// Hands dealerHand = new Hands(yourDeck, nrofplayers);
// Hands playerHands = new Hands(yourDeck, nrofplayers);
// if(nrofplayers<2){
// if (Deck.getValue(yourHand) > 21) {
// cash = cash - bet;
// System.out.println("bust");
// System.out.println("Your hand " + yourHand + Deck.getValue(yourHand));
// if (newbet > 0) {
// cash = cash - bet;
// }
// continue;
// } // while (Deck.getValue(Hand) < 21 && Deck.getValue(dealer) < 17) {
// // yourDeck = deckCheck(yourDeck);
// dealer.add(myDeck.deal());
// }
// if ((Deck.getValue(Hand) <= 21 && Deck.getValue(dealer) > 21)
// || (Deck.getValue(Hand) > Deck.getValue(dealer) && Deck.getValue(dealer) <
// 21)) {
// cash = cash + bet;
// if (newbet > 0) {
// cash = cash + bet;
// }
// } else if (Deck.getValue(Hand) == Deck.getValue(dealer)
// || Deck.getValue(Hand) < Deck.getValue(dealer)) {
// cash = cash - bet;
// if (newbet > 0) {
// cash = cash - bet;
// }
// }
// System.out.println("Dealers hand " + dealer + Deck.getValue(dealer));
// System.out.println("Your hand " + Hand + Deck.getValue(Hand));