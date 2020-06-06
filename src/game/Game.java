package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
	
	private String playerName;
	private String teamName;
	private List<Player> CPUplayers;
	private HumanPlayer gamePlayer;
	private Player[] playerOrder;
	private Player[] betOrder;
	private Round currRound;
	private Team[] teams;
	private Deck deck;
	private int pointGoal;
	
	
	public Game(String name, String teamName, int goal) {
		this.teamName = teamName;
		this.playerName = name;
		this.pointGoal = goal;
		initialise();
	}
	
	public void initialise() {
		Team[] teams = new Team[2];
		teams[0] = new Team(this.teamName);
		teams[1] = new Team("CPU Team");
		this.gamePlayer = new HumanPlayer(this.playerName, this, team1);
		Player cpu1 = new CPUPlayer("Your teammate", this, team1, new NoviceLevel());
		Player cpu2 = new CPUPlayer("Opponent 1", this, team2, new NoviceLevel());
		Player cpu3 = new CPUPlayer("Opponent 2", this, team2, new NoviceLevel());
		team1.addPlayer(this.gamePlayer);
		team1.addPlayer(cpu1);
		team2.addPlayer(cpu2);
		team2.addPlayer(cpu3);
		this.CPUplayers = new ArrayList<>();
		this.CPUplayers.add(cpu1);
		this.CPUplayers.add(cpu2);
		this.CPUplayers.add(cpu3);
		
		playerOrder = new Player[4];
		playerOrder[0] = gamePlayer;
		playerOrder[1] = cpu2;
		playerOrder[2] = cpu1;
		playerOrder[3] = cpu3;
		betOrder = new Player[4];
		betOrder[0] = gamePlayer;
		betOrder[1] = cpu2;
		betOrder[2] = cpu1;
		betOrder[3] = cpu3;
		
		this.deck = new Deck();
        this.deck.shuffle();
        
		this.currRound = new Round(this, deck, betOrder);
				
        
	}
	
	
	public void reset() {
		initialise();
	}
	
	public void startRound() {
		this.currRound.start();
	}
	
	public Player[] getPlayerOrder() {
		return playerOrder;
	}
	
	public void updateBetOrder() {
		Player temp = betOrder[0];
		for (int i = 0; i < 3; i++) {
			betOrder[i] = betOrder[i+1]; 
		}
		betOrder[3] = temp;
	}
	
	public void finishRound(Map<Team, Integer> roundScores) {
		// add scores to teams
		for (int i = 0; i < 2; i++) {
			teams[i].updateScore(roundScores.get(teams[i]));
			if (teams[i].getScore() >= 500) gameVictory(teams[i]);
			if (teams[i].getScore() <= -500) gameLoss(teams[i]);
		}
		if (gameEnded()) {
			
		} else {
			
			
		}
		// check if any team has hit +/- pointgoal
		// if they have, put up message saying won and ask to play again
		// if not, start a new round
		// update order
		
		// reset all the trump suit cards except joker then start new round
		
	}
	
	public void startNewRound() {
		updateBetOrder();
		resetTrumpSuit();
		deck.shuffle();
		currRound = new Round(this, deck, betOrder);
		currRound.start();
	}
	
}
