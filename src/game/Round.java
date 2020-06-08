package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Round {
	
	private Game game;
	private Deck deck;
	private Suit trumpSuit;
	private SimpleStringProperty trumpSuitString;
	private List<Card> kitty;
	private Player betWinner;
	private Bet currBet;
	private SimpleStringProperty currBetString;
	private SimpleStringProperty currStartingSuitString;
	private Trick[] tricks;
	private Player[] betOrder;
	private Player[] currOrder;
	private int currTrick;
	private int currBetter;
	private HumanPlayer gamePlayer;
	private List<Player> passedBetPlayers;
	private SimpleBooleanProperty bettingRound;
	private SimpleBooleanProperty kittyRound;
	private SimpleBooleanProperty tricksRound;
	
	public Round(Game game, Deck deck, HumanPlayer gamePlayer) {
		this.game = game;
		this.deck = deck;
		this.gamePlayer = gamePlayer;
		this.trumpSuitString = new SimpleStringProperty("None");
		this.currBetString = new SimpleStringProperty("None");
		this.currStartingSuitString = new SimpleStringProperty("None");
		this.bettingRound = new SimpleBooleanProperty(false);
		this.kittyRound = new SimpleBooleanProperty(false);
		this.tricksRound = new SimpleBooleanProperty(false);
		initialize();
	}
	
	public void initialize() {
		this.trumpSuit = null;
		this.betWinner = null;
		this.currBet = null;
		this.tricks = new Trick[10];
		this.betOrder = game.getBetOrder();
		this.currOrder = new Player[4];
		this.currTrick = 0;
		this.currBetter = 0;		
		this.passedBetPlayers = new ArrayList<>();
		this.kitty = new ArrayList<>();
		this.trumpSuitString.set("None");
		this.currBetString.set("None");
		this.currStartingSuitString.set("None");
		this.bettingRound.set(false);
		this.kittyRound.set(false);
		this.tricksRound.set(false);

	}
	
	public SimpleStringProperty getTrumpSuitString() {
		return trumpSuitString;
	}

	public SimpleStringProperty getCurrBetString() {
		return currBetString;
	}

	public SimpleStringProperty getCurrStartingSuitString() {
		return currStartingSuitString;
	}

	public void reset() {
		initialize();
	}
	
	public SimpleBooleanProperty getBettingRound() {
		return bettingRound;
	}

	public void setBettingRound(SimpleBooleanProperty bettingRound) {
		this.bettingRound = bettingRound;
	}

	public SimpleBooleanProperty getKittyRound() {
		return kittyRound;
	}

	public void setKittyRound(SimpleBooleanProperty kittyRound) {
		this.kittyRound = kittyRound;
	}

	public SimpleBooleanProperty getTricksRound() {
		return tricksRound;
	}

	public void setTricksRound(SimpleBooleanProperty tricksRound) {
		this.tricksRound = tricksRound;
	}

	public Suit getTrumpSuit() {
		// TODO Auto-generated method stub
		return trumpSuit;
	}
	public int getCurrBetSize() {
		return (currBet == null) ? 0:getCurrBet().getBet();
	}
	public int getCurrBetOrder() {
		return currBet.getOrder();
	}

	public Bet getCurrBet() {
		// TODO Auto-generated method stub
		return currBet;
	}
	
	public void dealCards() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 4; j++) {
				betOrder[j].addCard(deck.dealCard());
			}
		}
		for (int i = 0; i < 3; i++) {
			kitty.add(deck.dealCard());
		}

	}
	
	public void changeOrder(Player player) {
		int index = Arrays.asList(game.getPlayerOrder()).indexOf(player);
		for (int i = 0; i < 4; i++, index++) {
			currOrder[i] = game.getPlayerOrder()[index%4];
		}
	}
	

	private void convertFromTrumpSuit() {
		for (int i = 0; i < 43; i++) {
			deck.getCards()[i].convertFromTrumpSuit();
		}		
	}

	private void convertToTrumpSuit(Suit trumpSuit) {
		for (int i = 0; i < 43; i++) {
			deck.getCards()[i].convertToTrumpSuit(trumpSuit);
		}
		
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
			scores.put(getNonBettingTeam(), tricksWon.get(getNonBettingTeam())*10);
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
	
	public void incrementBetter() {
		currBetter = (currBetter + 1)%4;
	}
	
	public void changeTrumpSuit(Suit suit) {
		trumpSuit = suit;
		trumpSuitString.set(trumpSuit.toString());

	}
	
	public boolean CPUPlayToHumanInput() {
		System.out.println("FIrst to bet is: " + betOrder[0]);
		if (trumpSuit == null) {
			this.bettingRound.set(true);
			for (int i = currBetter; i < 4; i = (i+1)%4) {
				if (passedBetPlayers.size() == 3) break;
				incrementBetter();
				if (passedBetPlayers.contains(betOrder[i])) continue;
				if(betOrder[i].equals(gamePlayer)) return true;
				betOrder[i].makeBet();
			}
			changeTrumpSuit(currBet.getSuit());
			betWinner = currBet.getPlayer();
			convertToTrumpSuit(trumpSuit);
			addKittyToWinnerHand();
			this.bettingRound.set(false);
		}
		if (betWinner.getCards().size() > 10) {
			this.kittyRound.set(true);
			changeOrder(this.betWinner);
			if (betWinner.equals(gamePlayer)) return true;
			betWinner.discardCard();
		}
		gamePlayer.resetSelectedCard();
		if (currTrick < 10) {
			this.kittyRound.set(false);
			this.tricksRound.set(true);
			if (tricks[currTrick] == null) createNewTrick();
			if (!tricks[currTrick].CPUPlayToHumanInput()) return true;
			changeOrder(tricks[currTrick].getWinner());
			currTrick++;
			this.tricksRound.set(false);
			return false;
		}
		
		Map<Team, Integer> roundScores = calculateRoundScores();
		convertFromTrumpSuit();
		game.updateScores(roundScores);
		return false;
	}
	
	public void addKittyToWinnerHand() {
		betWinner.getCards().addAll(kitty);
	}
	
	public void createNewTrick() {
		tricks[currTrick] = new Trick(this, gamePlayer, currOrder);
		updatePlayerTricks();
	}
	
	public void updatePlayerTricks() {
		for (int i = 0; i < 4; i++) betOrder[i].updateNewTrick(tricks[currTrick]);
	}

	public void setCurrBet(Bet bet) {
		currBet = bet;
		currBetString.set(currBet.toString());
		
	}

	public void addPassedBetPlayers(Player player) {
		passedBetPlayers.add(player);
		
	}

	public void updateStartingSuitString(Suit startingSuit) {
		currStartingSuitString.set(startingSuit.toString());
	}
	
	
	
}
