package game;

import java.util.List;

public class HumanPlayer extends Player {
	
	private Bet humanBet;
	private Card humanCard;
	
	public HumanPlayer(String name, Game game, Team team) {
		super(name, game, team);
		humanInitialise();
	}
	
	public void humanInitialise() {
		humanBet = null;
		humanCard = null;
	}

	// TODO
	@Override
	public void makeBet() {
		if (humanBet == null) getCurrRound().addPassedBetPlayers(this);
		else getCurrRound().setCurrBet(humanBet);
		// getCurrRound().incrementBetter();
	}
	
	public boolean playCard() {
		if (humanCard == null) return false;
		if (!checkCardPlay(humanCard)) return false;
		getHand().removeCard(humanCard);
		setCurrPlayedCard(humanCard);
		getCurrTrick().cardPlayed(humanCard);
		return true;
	}

	public void discardCard() {
		getHand().removeCard(humanCard);
	}
	
	public boolean addSelection(Card c) {
		if (c.equals(humanCard)) {
			humanCard = null;
			return false;
		}

		humanCard = c;
		return true;
		
	}
	
	public boolean checkSelection(Card c) {
		if (!addSelection(c)) return false;
		return checkCardPlay(c);
	}

	public void setHumanBet(Bet bet) {
		humanBet = bet;
		
	}
	
	@Override
	public void reset() {
		super.reset();
		initialise();
	}
	
	public void resetSelectedCard() {
		this.humanCard = null;
	}

}
