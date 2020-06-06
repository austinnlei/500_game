package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round {
	
	private Game game;
	private Deck deck;
	private Suit trumpSuit;
	private List<Card> kitty;
	private Player betWinner;
	private Bet currBet;
	private Trick[] tricks;
	private Player[] betOrder;
	private Player[] currOrder;
	
	public Round(Game game, Deck deck, Player[] betOrder) {
		this.game = game;
		this.deck = deck;
		this.trumpSuit = null;
		this.kitty = null;
		this.betWinner = null;
		this.currBet = null;
		this.tricks = new Trick[10];
		this.betOrder = betOrder;
		this.currOrder = new Player[4];
		
	}
	
	public Suit getTrumpSuit() {
		// TODO Auto-generated method stub
		return trumpSuit;
	}
	public int getCurrBetSize() {
		return currBet.getBet();
	}
	public int getCurrBetOrder() {
		return currBet.getOrder();
	}
	public void setBet(Bet bet) {
		this.currBet = bet;
		
	}
	public Bet getCurrBet() {
		// TODO Auto-generated method stub
		return currBet;
	}
	
	// TODO: when you figure this out - compare with bet and probs return a map of team with scores to be updated
	public void determineWinner() {
		
	}
	
	public List<Card> dealCards() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 4; j++) {
				betOrder[j].addCard(deck.dealCard());
			}
		}
		List<Card> kitty = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			kitty.add(deck.dealCard());
		}
		return kitty;
	}
	
	public void changeOrder(Player player) {
		int index = Arrays.asList(game.getPlayerOrder()).indexOf(player);
		for (int i = 0; i < 4; i++, index++) {
			currOrder[i] = game.getPlayerOrder()[index%4];
		}
	}
	
	public void playTricks() {
		for (int i = 0; i < 10; i++) {
			tricks[i] = new Trick(this, currOrder);
			tricks[i].start();
			changeOrder(tricks[i].getWinner());
		}
	}
	
	public void doBets() {
		List<Player> passedPlayers = new ArrayList<>();
		while(true) {
			for (int i = 0; i < 4; i++) {
				if (passedPlayers.contains(betOrder[i])) continue;
				currBet = betOrder[i].makeBet();
				if (currBet != null) {
					betWinner = betOrder[i];
				} else {
					passedPlayers.add(betOrder[i]);
					if (passedPlayers.size() == 3) break;
				}
			}
			if (passedPlayers.size() == 3) break;
		}
	}
	
	public void start() {
		// deal cards
		List<Card> kitty = dealCards();
		// do betting round
		doBets();
		// do kitty changes
		betWinner.selectHandWithKitty(kitty);
		changeOrder(this.betWinner);
		// start loop for trick playing
		playTricks();
		
		// count all the trick winners, determine scores to send back to game
		Map<Team, Integer> roundScores = calculateRoundScores();
		game.finishRound(roundScores);
	}

	public Map<Team, Integer> calculateRoundScores() {
		Map<Team, Integer> scores = new HashMap<>();
		Map<Team, Integer> tricksWon = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			Team winningTeam = tricks[i].getWinner().getTeam();
			int count = tricksWon.containsKey(winningTeam) ? tricksWon.get(winningTeam) : 0;
			tricksWon.put(winningTeam, count + 1);
		}
		if (tricksWon.get(getBettingTeam()) >= getCurrBetSize()) {
			scores.put(getBettingTeam(), currBet.calcBetPoints());
			scores.put(getNonBettingTeam(), 0);
		} else {
			scores.put(getBettingTeam(), (-1)*currBet.calcBetPoints());
			scores.put(getNonBettingTeam(), tricksWon.get(getNonBettingTeam()));
		}
		
		return scores;
	}
	
	private Team getBettingTeam() {
		return betWinner.getTeam();
	}
	private Team getNonBettingTeam() {
		for (int i = 0; i < 4; i++) {
			if (!betOrder[i].getTeam().equals(getBettingTeam())) return betOrder[i].getTeam();
		}
		return null;
	}

	public boolean checkBet(Suit suit, int num) {
		if (num < getCurrBetSize()) return false;
		if (num == getCurrBetSize() && suit.getOrder() <= getCurrBetOrder()) return false;
		return true;
	}
	
	
	
}
