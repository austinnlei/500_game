package game;

import java.util.ArrayList;
import java.util.List;

public class Trick {
	private Player winner;
	private Player[] order;
	private Suit startingSuit;
	private Round round;
	private Card[] cardsPlayed;
	
	
	public Trick(Round round, Player[] order) {
		this.winner = null;
		this.order = order;
		this.round = round;
		this.startingSuit = null;
		this.cardsPlayed = new Card[4];
	}


	public Suit getStartingSuit() {
		// TODO Auto-generated method stub
		return startingSuit;
	}

	public Player getWinner() {
		return winner;
	}

	public void start() {
		// starting player plays a card
		// loop through all players to play a card
		// determine winner
		boolean trumpPlayed = false;
		for (int i = 0; i < 4; i++) {
			cardsPlayed[i] = (order[i].playCard());
			if (cardsPlayed[i].getSuit() == Suit.TRUMPSUIT) trumpPlayed = true;
			if (startingSuit == null) startingSuit = cardsPlayed[0].getSuit();
		}
		Suit suitToCheck = startingSuit;
		if (trumpPlayed) suitToCheck = Suit.TRUMPSUIT;
		for (int i = 0; i < 4; i++) {
			int max = 0;
			if (cardsPlayed[i].getSuit() == suitToCheck && cardsPlayed[i].getValue() > max) {
				max = cardsPlayed[i].getValue();
				winner = order[i];
			}
		}
		
	}
	


}
