package game;

public class Bet {
	private Player player;
	private Suit suit;
	private int bet;
	
	public Bet(Player player, Suit suit, int bet) {
		this.player = player;
		this.suit = suit;
		this.bet = bet;
	}
	
	public int calcBetPoints() {
		switch(suit) {
			case SPADES:
				if (bet == 6) return 40;
				if (bet == 7) return 140;
				if (bet == 8) return 240;
				if (bet == 9) return 340;
				if (bet == 10) return 440;
				break;
			case CLUBS:
				if (bet == 6) return 160;
				if (bet == 7) return 160;
				if (bet == 8) return 260;
				if (bet == 9) return 360;
				if (bet == 10) return 460;
				break;
			case DIAMONDS:
				if (bet == 6) return 80;
				if (bet == 7) return 180;
				if (bet == 8) return 280;
				if (bet == 9) return 380;
				if (bet == 10) return 480;
				break;
			case HEARTS:
				if (bet == 6) return 100;
				if (bet == 7) return 200;
				if (bet == 8) return 300;
				if (bet == 9) return 400;
				if (bet == 10) return 500;
				break;
			default:
				return -1;
		}
		return -1;
	}

	public int getBet() {
		return bet;
	}

	public Player getPlayer() {
		return player;
	}

	public Suit getSuit() {
		return suit;
	}
	
	public int getOrder() {
		return suit.getOrder();
	}
}
