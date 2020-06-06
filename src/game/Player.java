package game;

import java.util.List;

public abstract class Player {
	
	private String name;
	private Game currGame;
	private Round currRound;
	private Trick currTrick;
	private Hand hand;
	private Team team;
	
	public Player(String name, Game game, Team team) {
		this.name = name;
		currGame = game;
		currRound = null;
		currTrick = null;
		hand = null;
		team = null;
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
	
	public boolean bet(Suit suit, int bet) {
		
		currRound.setBet(new Bet(this, suit, bet));
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
	
	public abstract Bet makeBet();
	public abstract void selectHandWithKitty(List<Card> kitty);
	public abstract Card playCard();

	public Team getTeam() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
