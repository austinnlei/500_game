package game;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	
	private Card[] cards;
	private int cardsUsed;
	
	public Deck() {
		cards = new Card[43];
		this.reset();
	}
	
	public void reset() {
		int cardCount = 0;
		for (Suit s: Suit.values()) {
			if (s == Suit.TRUMPSUIT) {
				cards[cardCount] = new Card(s, Value.JOKER);
				cardCount++;
			} else if (s.getColour() == "Red") {
				for (Value v: Value.values()) {
					if (v == Value.TWO || v == Value.THREE || v == Value.FOUR ||
						v == Value.ALTTRUMPJACK || v == Value.TRUMPJACK || v == Value.JOKER) {
						continue;
					}
					cards[cardCount] = new Card(s, v);
					cardCount++;
				}
			} else {
				for (Value v: Value.values()) {
					if (v == Value.TWO || v == Value.THREE || v == Value.ALTTRUMPJACK || 
						v == Value.TRUMPJACK || v == Value.JOKER) {
						continue;
					}
					cards[cardCount] = new Card(s, v);
					cardCount++;
				}
			}
		}
		cardsUsed = 0;
	}
	
	public void shuffle() {
		for (int i = cards.length-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Card temp = cards[i];
            cards[i] = cards[rand];
            cards[rand] = temp;
        }
		cardsUsed = 0;
	}
	
	//TODO
	public Card dealCard() {
		cardsUsed++;
		return cards[cardsUsed - 1];
	}
	
	// TODO: something to do with kiddy
	
}
