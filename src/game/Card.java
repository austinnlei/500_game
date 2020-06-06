package game;

public class Card {
	private Suit suit;
	private Value value;
	private boolean isFaceUp;
	private boolean isTrumpSuit;
	
	public Card(Suit suit, Value value) {
		this.suit = suit;
		this.value = value;
		this.isFaceUp = false;
		if (suit == Suit.TRUMPSUIT) {
			this.isTrumpSuit = true;
		} else {
			this.isTrumpSuit = false;
		}
		
	}
	
	public int getValue() {
		 return value.getValue();
	}
	
	public Suit getSuit() {
		if (isTrumpSuit) return Suit.TRUMPSUIT;
		return suit;
	}
	
	
	public String getColour() {
		return suit.getColour();
	}
	
	public void convertToTrumpSuit(Suit suit) {
		if (this.suit == suit) {
			this.isTrumpSuit = true;
		}
		if (this.value == value.JACK) {
			if (this.suit == suit) {
				this.value = value.TRUMPJACK;
			} else if (getColour() == suit.getColour()) {
				this.isTrumpSuit = true;
				this.value = value.ALTTRUMPJACK;
			}
		}
	}
	
	public void convertFromTrumpSuit() {
		if (this.value == value.JOKER) return;
		if (this.isTrumpSuit) {
			this.isTrumpSuit = false;
			if (this.value == value.TRUMPJACK || this.value == value.ALTTRUMPJACK) this.value = value.JACK;
		}
	}

	public int getSuitOrder() {
		// TODO Auto-generated method stub
		return suit.getOrder();
	}
	
	
}
