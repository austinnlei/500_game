package game;

public enum Suit {
	HEARTS(4, "Hearts", "Red"),
	SPADES(2, "Spades", "Black"),
	DIAMONDS(3, "Diamonds", "Red"),
	CLUBS(1, "Clubs", "Black"),
	TRUMPSUIT(5, "Trump", "Trump");
	
	private final int order;
	private final String name;
	private final String colour;
	
	private Suit(int order, String name, String colour) {
		this.order = order;
		this.name = name;
		this.colour = colour;
	}
	
	
	
	public String getColour() {
		return colour;
	}



	public int getOrder() {
		return order;
	}
}
