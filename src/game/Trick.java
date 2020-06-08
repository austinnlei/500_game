package game;

import java.util.ArrayList;
import java.util.List;

public class Trick {
	private Player winner;
	private Player[] order;
	private Suit startingSuit;
	private Round round;
	private Card[] cardsPlayed;
	private int currCard;
	private Player gamePlayer;
	private boolean trumpPlayed;
	
	
	public Trick(Round round, Player gamePlayer, Player[] order) {
		this.winner = null;
		this.order = order;
		this.round = round;
		this.startingSuit = null;
		this.cardsPlayed = new Card[4];
		this.currCard = 0;
		this.gamePlayer = gamePlayer;
		this.trumpPlayed = false;
	}


	public Suit getStartingSuit() {
		// TODO Auto-generated method stub
		return startingSuit;
	}

	public Player getWinner() {
		return winner;
	}
	
	public void cardPlayed(Card c) {
		
		cardsPlayed[currCard] = c;
		
		if (cardsPlayed[currCard].getSuit() == Suit.TRUMPSUIT) trumpPlayed = true;
		if (startingSuit == null) {
			startingSuit = cardsPlayed[0].getSuit();
			round.updateStartingSuitString(startingSuit);
		}
		currCard++;
	}


	public boolean CPUPlayToHumanInput() {
		for (int i = currCard; i < 4; i++) {
			System.out.println(order[i] + order[i].getName());
			System.out.println(gamePlayer + gamePlayer.getName());
			if (order[i].equals(gamePlayer)) return false;
			order[i].playCard();
		}
		Suit suitToCheck = startingSuit;
		if (trumpPlayed) suitToCheck = Suit.TRUMPSUIT;
		System.out.println("Checking winner");
		int max = 0;
		for (int i = 0; i < 4; i++) {
			
			if (cardsPlayed[i].getSuit() == suitToCheck && cardsPlayed[i].getValue() > max) {
				max = cardsPlayed[i].getValue();
				winner = order[i];
			}
		}

		return true;
	}
	


}
