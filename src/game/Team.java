package game;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Team {
	
	private String name;
	private List<Player> players;
	private int score;
	private SimpleStringProperty stringScore;
	
	public Team(String name) {
		this.name = name;
		this.players = new ArrayList<>();
		this.score = 0;
		this.stringScore = new SimpleStringProperty(Integer.toString(score));
	}
	
	public void updateScore(int scoreUpdate) {
		this.score += scoreUpdate;
		this.stringScore.set(Integer.toString(score));
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	
	public SimpleStringProperty getStringScore() {
		return stringScore;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void reset() {
		this.score = 0;
		this.stringScore.set(Integer.toString(score));
	}
}
