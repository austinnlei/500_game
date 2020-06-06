package game;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {

	@Override
	public int compare(Card c1, Card c2) {
		if (c1.getValue() == c2.getValue()) return (c1.getSuitOrder() - c2.getSuitOrder());
		return c1.getValue() - c2.getValue();
	}
}
