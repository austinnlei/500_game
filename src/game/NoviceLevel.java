package game;

import java.util.Collections;
import java.util.List;

public class NoviceLevel implements SkillLevel {
	@Override
	public Card selectCard(CPUPlayer player) {
		if (player.getStartingSuit() == null) return player.findHighestCard();
		if (player.haveStartingSuit()) return player.findHighestCardOfSuit(player.getStartingSuit());
		if (player.haveCertainSuit(Suit.TRUMPSUIT)) return player.findHighestTrumpCard();
		return player.findLowestCard();
	}

	@Override
	public Bet selectBet(CPUPlayer player) {
		if(player.getCurrRound().checkBet(player.getSuitWithMaxCards(), determineMaxBet(player))) return new Bet(player, player.getSuitWithMaxCards(), determineMaxBet(player));
		else return null;
	}

	@Override
	public int determineMaxBet(CPUPlayer player) {
		switch (player.getNumCardsOfMaxSuit()) {
			case(4):
				return 6;
			case(5):
				return 7;
			case(6):
				return 7;
			case(7):
				return 8;
			case(8):
				return 8;
			case(9):
				return 9;
			case(10):
				return 10;
		}
		return 6;
	}
	
	@Override
	public void selectNewHandAfterKitty(Hand originalHand, List<Card> kitty) {
		for (Card c: kitty) originalHand.addCard(c);
		Collections.sort(originalHand.getCards(), new CardComparator());
		originalHand.removeFirstThreeCards();
	}
}
