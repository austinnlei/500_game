package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPUPlayer extends Player {
	
	private SkillLevel level;
	
	public CPUPlayer(String name, Game game, Team team, SkillLevel level) {
		super(name, game, team);
		this.level = level;
	}
	
	public Card findHighestCardOfSuit(Suit suit) {
		Card highestCard = null;
		for (Card c: getCards()) {
			if (c.getSuit().equals(suit)) {
				if (highestCard == null) highestCard = c;
				else if (c.getValue() > highestCard.getValue()) highestCard = c;
			}
		}
		return highestCard;
	}
	
	public Card findHighestTrumpCard() {
		return findHighestCardOfSuit(Suit.TRUMPSUIT);
	}
	
	public Card findHighestCard() {
		Card highestCard = null;
		for (Card c: getCards()) {
			if (highestCard == null) highestCard = c;
			else if (c.getValue() > highestCard.getValue()) highestCard = c;				
		}
		return highestCard;
	}
	
	public Card findLowestCard() {
		Card lowestCard = null;
		for (Card c: getCards()) {
			if (lowestCard == null) lowestCard = c;
			else if (c.getValue() < lowestCard.getValue()) lowestCard = c;				
		}
		return lowestCard;
	}
	
	private Map<Suit, Integer> getSuitNum() {
		Map<Suit, Integer> suitDict = new HashMap<>();
		for (Card c: getCards()) {
			int count = suitDict.containsKey(c.getSuit()) ? suitDict.get(c.getSuit()) : 0;
			suitDict.put(c.getSuit(), count + 1);
		}
		return suitDict;
	}
	
	public Suit getSuitWithMaxCards() {
		Map<Suit, Integer> suitDict = getSuitNum();
		return Collections.max(suitDict.entrySet(), Map.Entry.comparingByValue()).getKey();
	}
	
	public int getNumCardsOfMaxSuit() {
		Map<Suit, Integer> suitDict = getSuitNum();
		return Collections.max(suitDict.entrySet(), Map.Entry.comparingByValue()).getValue();
	}
	
	public Card selectCardToPlay() {
		if (!checkCardPlay(level.selectCard(this))) System.out.println("Error with card play algorithm");
		return level.selectCard(this);
	}
	
	@Override
	public void makeBet() {
		Bet CPUBet = level.selectBet(this);
		if (CPUBet == null) getCurrRound().addPassedBetPlayers(this);
		else getCurrRound().setCurrBet(CPUBet);

	}
	
	@Override
	public void discardCard() {
		level.selectNewHandAfterKitty(getHand());
	}

	@Override
	public boolean playCard() {
		Card cardToPlay = level.selectCard(this);
		getHand().removeCard(cardToPlay);
		setCurrPlayedCard(cardToPlay);
		getCurrTrick().cardPlayed(cardToPlay);
		return true;
	}

}
