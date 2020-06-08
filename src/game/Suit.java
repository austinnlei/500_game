package game;

public enum Suit {
	HEARTS(4, "hearts", "Red"),
	SPADES(2, "spades", "Black"),
	DIAMONDS(3, "diamonds", "Red"),
	CLUBS(1, "clubs", "Black"),
	TRUMPSUIT(5, "trump", "Trump");
	
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
	
	public String toString() {
		return name;
	}
}
