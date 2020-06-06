package game;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> cards;
	
	public Hand() {
		cards = new ArrayList<>();
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	//TODO
	public void arrangeHand() {
		
	}

	public List<Card> getCards() {
		// TODO Auto-generated method stub
		return cards;
	}
	
	public void removeFirstThreeCards() {
		cards.subList(0, 3).clear();
	}
}
