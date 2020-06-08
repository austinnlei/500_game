package game;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public abstract class Player {
	
	private String name;
	private Game currGame;
	private Round currRound;
	private Trick currTrick;
	private Card currPlayedCard;
	private Hand hand;
	private Team team;
	
	public Player(String name, Game game, Team team) {
		this.name = name;
		currGame = game;
		this.team = team;
		
		initialise();
	}
	
	public String getName() {
		return name;
	}

	public void initialise() {
		currRound = null;
		currTrick = null;
		currPlayedCard = null;
		this.hand = new Hand();
		
	}
	
	public void addCard(Card card) {

		this.hand.addCard(card);
	}
	
	public boolean haveStartingSuit() {
		if (currTrick.getStartingSuit() == null) {
			return false;
		}
		return haveCertainSuit(currTrick.getStartingSuit());
	}
	
	public boolean checkCardPlay(Card card) {
		if (haveStartingSuit()) {
			if (!card.getSuit().equals(currTrick.getStartingSuit())) return false;
		}
		return true;
	}

	

	
	public boolean haveCertainSuit(Suit suit) {
		for (Card card : hand.getCards()) {
			if (card.getSuit().equals(suit)) return true;
		}
		return false;
	}
	

	public Bet getCurrBet() {
		return currRound.getCurrBet();
	}
	

	public List<Card> getCards() {
		// TODO Auto-generated method stub
		return hand.getCards();
	}
	
	public Suit getStartingSuit() {
		return currTrick.getStartingSuit();
	}
	
	
	
	public Hand getHand() {
		return hand;
	}

	public Round getCurrRound() {
		return currRound;
	}
	
	public abstract void makeBet();
	public abstract boolean playCard();
	public abstract void discardCard();

	public Team getTeam() {
		// TODO Auto-generated method stub
		return team;
	}


	public void updateNewTrick(Trick trick) {
		this.currTrick = trick;
		this.currPlayedCard = null;
		
	}
	
	public void setCurrRound(Round round) {
		this.currRound = round;
		this.currPlayedCard = null;
		
	}
	
	public void setCurrPlayedCard(Card c) {
		this.currPlayedCard = c;
	}
	
	public Card getCurrPlayedCard() {
		return this.currPlayedCard;
	}
	
	public Trick getCurrTrick() {
		return currTrick;
	}

	public SimpleStringProperty getNameAndTeam() {
		// TODO Auto-generated method stub
		return new SimpleStringProperty(name + " (" + getTeam().getName()+")");
	}

	public void reset() {
		initialise();
	}
	
	public String toString() {
		return name;
	}
	
}
