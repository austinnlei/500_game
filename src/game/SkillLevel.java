package game;

import java.util.List;

public interface SkillLevel {
	public Card selectCard(CPUPlayer player);
	public int determineMaxBet(CPUPlayer player);
	public Bet selectBet(CPUPlayer player);
	public void selectNewHandAfterKitty(Hand hand);
}
