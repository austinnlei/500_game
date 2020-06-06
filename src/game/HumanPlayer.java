package game;

import java.util.List;

public class HumanPlayer extends Player {
	public HumanPlayer(String name, Game game, Team team) {
		super(name, game, team);
	}

	// TODO
	@Override
	public Bet makeBet() {
		// TODO Auto-generated method stub
		// link to UI
		if (selects not to bet) return null;
		if (getCurrRound().checkBet(input(Suit), input(num))) return new Bet(this, input(Suit), input(num));
		// throw error msg saying chosen bet was invalid
		else return this.makeBet();
	}

	@Override
	public void selectHandWithKitty(List<Card> kitty) {
		getCards().addAll(kitty);
		// TODO Auto-generated method stub
		// link to UI
		getCards().remove(input(Card1))
		getCards().remove(input(Card2))
		getCards().remove(input(Card3))
		
	}

	@Override
	public Card playCard() {
		// Link to UI, some way for player to select a card
		if (checkCardPlay(input(card))) return input(card);
		// print that the card chosen was invalid
		return playCard();
	}

}
