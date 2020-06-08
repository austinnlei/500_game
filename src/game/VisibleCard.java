package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class VisibleCard extends StackPane {

	private boolean selected = false;
	private Rectangle base;
	private Rectangle mask;
	private Boolean isTrump;
	private Rectangle trumpMask;
	private String code;

	
	public VisibleCard() {
		setCard("0s", false);
	}


	public VisibleCard(String code, Boolean isTrump) {
		setCard(code, isTrump);
	}


	/**
	 * Sets the card according to the value and suit found in the card code string.
	 * @param code
	 */
	public void setCard(String code, boolean isTrump) {
		this.getChildren().clear();
		if (code.equals("0s")) {
			createFrame();
		} else {
			this.code = code;
			this.isTrump = isTrump;
			createCard();
		}
	}


	/**
	 * Creates a frame for showing an empty card slot
	 */
	private void createFrame() {
		base = createBase(Color.TRANSPARENT);
		base.setStroke(Color.DARKGREY);
		this.getChildren().add(base);
	}


	/**
	 * Creates a card with a white base, a mask layer that will be shown when the card is selected,
	 * and the image specified in the constructor
	 */
	private void createCard() {
		base = createBase(Color.WHITE);
		base.setStroke(Color.BLACK);

		mask = createBase(Color.ORANGE);
		mask.setOpacity(0);
		
		trumpMask = createBase(Color.BLUE);
		if (isTrump) trumpMask.setOpacity(0.3);
		else trumpMask.setOpacity(0);

		ImageView image = setImage();
		this.getChildren().addAll(base, mask, trumpMask, image);
	}


	/**
	 * General method for creating a base of any color
	 * @param color the desired color
	 * @return
	 */
	private Rectangle createBase(Color color) {
		Rectangle r = new Rectangle(75, 110);
		r.setFill(color);
		r.setArcHeight(7);
		r.setArcWidth(7);
		return r;
	}


	/**
	 * Sets the image to be used in the card
	 * @return the image to be shown
	 */
	private ImageView setImage() {
		ImageView image = new ImageView();
		image.setImage(new Image(GameApplication.class.getResourceAsStream("cards/"+code+".png")));
		image.setPreserveRatio(true);
		image.setFitWidth(72);
		return image;
	}


	/**
	 * Makes the mask visible or invisible, depending whether the card is selected or not.
	 */
	public void select() {
		if (selected) {
			setTranslateY(getTranslateY()+10);
			mask.setOpacity(0);
			if (isTrump) trumpMask.setOpacity(0.3);
			selected = false;
		}
		else {
			setTranslateY(getTranslateY()-10);
			mask.setOpacity(0.3);
			if (isTrump) trumpMask.setOpacity(0);
			selected = true;
		}
	}


	public String toString() {
		return code;
	}


	public void deselect() {
		if (selected) {
			setTranslateY(getTranslateY()+10);
			mask.setOpacity(0);
			if (isTrump) trumpMask.setOpacity(0.3);
			selected = false;
		}
	}
}

