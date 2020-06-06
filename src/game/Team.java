package game;

import java.util.ArrayList;
import java.util.List;

public class Team {
	
	private String name;
	private List<Player> players;
	private int score;
	
	public Team(String name) {
		this.name = name;
		this.players = new ArrayList<>();
		this.score = 0;
	}
	
	public void updateScore(int scoreUpdate) {
		this.score += scoreUpdate;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
}
