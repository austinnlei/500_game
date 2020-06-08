package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Game {
	
	private GameController gc;
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
	
	
	public Game(GameController gc, String name, String teamName, int goal) {
		this.gc = gc;
		this.teamName = teamName;
		this.playerName = name;
		this.pointGoal = goal;
		this.deck = new Deck();
		this.teams = new Team[2];
		teams[0] = new Team(this.teamName);
		teams[1] = new Team("CPU Team");
		this.gamePlayer = new HumanPlayer(this.playerName, this, teams[0]);
		Player cpu2 = new CPUPlayer("Your teammate", this, teams[0], new NoviceLevel());
		Player cpu1 = new CPUPlayer("Opponent 1", this, teams[1], new NoviceLevel());
		Player cpu3 = new CPUPlayer("Opponent 2", this, teams[1], new NoviceLevel());
		teams[0].addPlayer(this.gamePlayer);
		teams[0].addPlayer(cpu2);
		teams[1].addPlayer(cpu1);
		teams[1].addPlayer(cpu3);
		this.CPUplayers = new ArrayList<>();
		this.CPUplayers.add(cpu1);
		this.CPUplayers.add(cpu2);
		this.CPUplayers.add(cpu3);
		this.playerOrder = new Player[4];
		playerOrder[0] = gamePlayer;
		playerOrder[1] = CPUplayers.get(0);
		playerOrder[2] = CPUplayers.get(1);
		playerOrder[3] = CPUplayers.get(2);
		this.currRound = new Round(this, deck, gamePlayer);
		
		this.betOrder = new Player[4];
		
		
		initialise();
	}
	
	public void initialise() {
		
		for (int i = 0; i < 4; i++) {
			betOrder[i] = playerOrder[i];
			playerOrder[i].reset();
		}
		
		teams[0].reset();
		
		teams[1].reset();
		 
		deck.shuffle();
		      
        createNewRound();
		

				
        
	}
	
	public void createNewRound() {
		deck.shuffle();
		currRound.reset();
		updatePlayerRound();
		currRound.dealCards();
		
	}
	
	public void reset() {
		
		initialise();
	}

	public Player[] getPlayerOrder() {
		return playerOrder;
	}
	
	public void updateBetOrder() {
		System.out.println("Currently updating bet order. first is: "+betOrder[0]);
		Player temp = betOrder[0];
		for (int i = 0; i < 3; i++) {
			betOrder[i] = betOrder[i+1]; 
		}
		betOrder[3] = temp;
		System.out.println("finished updating bet order. first is: "+betOrder[0]);
	}
	
	public void updateScores(Map<Team, Integer> roundScores) {
		for (int i = 0; i < 2; i++) {
			teams[i].updateScore(roundScores.get(teams[i]));
		}
		checkOutcomeOfGame();
		
	}
	
	public void checkOutcomeOfGame() {
		updateBetOrder();
		if (!gameHasEnded()) {
			this.gc.showPopUp("Continuing on with next round...", "This round has ended but no team has won.");
			createNewRound();
		} else {
			Team winningTeam = findWinningTeam();
			this.gc.showOptionPopUp(winningTeam.getName() + " has won!", "Congratulations if you won! Regardless, would you like to play again?");
		}
		
	}
	
	public Team findWinningTeam() {
		for (int i = 0; i < 2; i++) {
			if (teams[i].getScore() >= pointGoal) return teams[i];
			if (teams[i].getScore() <= (-1)*pointGoal) return teams[(i+1)%2];
		}
		return null;

	}
	
	public boolean gameHasEnded() {
		for (int i = 0; i < 2; i++) {
			if (teams[i].getScore() >= pointGoal || teams[i].getScore() <= (-1)*pointGoal) return true;
		}
		return false;
	}
	
	public void addSelection(Card c) {
		gamePlayer.addSelection(c);
	}
	
	public boolean checkSelection(Card c) {
		return gamePlayer.checkSelection(c);
	}

	public boolean humanPlayCard() {
		return gamePlayer.playCard();
		
	}
	
	public boolean humanBet(Suit suit, int num) {
		if (!currRound.checkBet(suit, num)) {
			return false;
		}
		gamePlayer.setHumanBet(new Bet(gamePlayer, suit, num));
		gamePlayer.makeBet();
		return true;
	}
	
	public boolean passBet() {
		if (currRound.getCurrBet() == null) return false;
		gamePlayer.setHumanBet(null);
		gamePlayer.makeBet();
		return true;
	}

	public boolean CPUPlayToHumanInput() {
		return currRound.CPUPlayToHumanInput();
	}

	public Round getCurrRound() {
		return currRound;
	}

	public List<Card> getHumanCards() {
		// TODO Auto-generated method stub
		return gamePlayer.getCards();
	}

	public void humanDiscardCard() {
		gamePlayer.discardCard();
		
	}
	
	public void updatePlayerRound() {
		for (int i = 0; i < 4; i++) betOrder[i].setCurrRound(currRound);
	}

	public Player getGamePlayer() {
		// TODO Auto-generated method stub
		return gamePlayer;
	}

	public Team getPlayerTeam() {
		// TODO Auto-generated method stub
		return teams[0];
	}
	
	public Team getCPUTeam() {
		// TODO Auto-generated method stub
		return teams[1];
	}

	public Player[] getBetOrder() {
		// TODO Auto-generated method stub
		return this.betOrder;
	}

	public Player getBottomPlayer() {
		// TODO Auto-generated method stub
		return playerOrder[0];
	}
	
	public Player getLeftPlayer() {
		// TODO Auto-generated method stub
		return playerOrder[1];
	}
	public Player getRightPlayer() {
		// TODO Auto-generated method stub
		return playerOrder[3];
	}
	public Player getTopPlayer() {
		// TODO Auto-generated method stub
		return playerOrder[2];
	}



	
}
