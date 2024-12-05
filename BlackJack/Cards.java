public enum Cards {
    //Hearts
    Ace_of_Hearts(11),
    Two_of_Hearts(2),
    Three_of_Hearts(3),
    Four_of_Hearts(4),
    Five_of_Hearts(5),
    Six_of_Hearts(6),
    Seven_of_Hearts(7),
    Eight_of_Hearts(8),
    Nine_of_Hearts(9),
    Ten_of_Hearts(10),
    Jack_of_Hearts(10),
    Queen_of_Hearts(10),
    King_of_Hearts(10),
    //Diamonds
    Ace_of_Diamonds(11),
    Two_of_Diamonds(2),
    Three_of_Diamonds(3),
    Four_of_Diamonds(4),
    Five_of_Diamonds(5),
    Six_of_Diamonds(6),
    Seven_of_Diamonds(7),
    Eight_of_Diamonds(8),
    Nine_of_Diamonds(9),
    Ten_of_Diamonds(10),
    Jack_of_Diamonds(10),
    Queen_of_Diamonds(10),
    King_of_Diamonds(10),
    //Spades
    Ace_of_Spades(11),
    Two_of_Spades(2),
    Three_of_Spades(3),
    Four_of_Spades(4),
    Five_of_Spades(5),
    Six_of_Spades(6),
    Seven_of_Spades(7),
    Eight_of_Spades(8),
    Nine_of_Spades(9),
    Ten_of_Spades(10),
    Jack_of_Spades(10),
    Queen_of_Spades(10),
    King_of_Spades(10),
    //Clubs
    Ace_of_Clubs(11),
    Two_of_Clubs(2),
    Three_of_Clubs(3),
    Four_of_Clubs(4),
    Five_of_Clubs(5),
    Six_of_Clubs(6),
    Seven_of_Clubs(7),
    Eight_of_Clubs(8),
    Nine_of_Clubs(9),
    Ten_of_Clubs(10),
    Jack_of_Clubs(10),
    Queen_of_Clubs(10),
    King_of_Clubs(10);
    private int value;
    private Cards(int value){// https://stackoverflow.com/questions/1067352/can-i-set-enum-start-value-in-java a reference on how to get values for enums
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    

}
