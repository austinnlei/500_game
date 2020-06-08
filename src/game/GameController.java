package game;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


/**
 * GUI controller class. 
 */
public class GameController implements Initializable {
	
	ObservableList<Suit> suitList = FXCollections.observableArrayList(Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS, Suit.SPADES);
	ObservableList<Integer> trickList = FXCollections.observableArrayList(6,7,8,9,10);

	
    @FXML private FlowPane playerCardsPane;
    @FXML private FlowPane tablePane;
    // Info
    @FXML private Label humanTeamScore;
    @FXML private Label CPUTeamScore;
    @FXML private Label currTrumpSuit;
    @FXML private Label currBet;
    @FXML private Label currStartingSuit;
    // Player Buttons
    @FXML private ChoiceBox betSuitChoice;
    @FXML private ChoiceBox betNumTricksChoice;
    @FXML private Button betButton;
    @FXML private Button passBetButton;
    @FXML private Button discardButton;
    @FXML private Button playButton;
    @FXML private Button continueButton;
    // Players
    @FXML private FlowPane bottomPlayerCardPane;
    @FXML private FlowPane leftPlayerCardPane;
    @FXML private FlowPane topPlayerCardPane;
    @FXML private FlowPane rightPlayerCardPane;
    @FXML private Label bottomPlayerTag;
    @FXML private Label leftPlayerTag;
    @FXML private Label topPlayerTag;
    @FXML private Label rightPlayerTag;

    @FXML
    void handleBet(ActionEvent event) {makeBet();}
    
    @FXML
    void handlePassBet(ActionEvent event) {passBet();}

    @FXML
    void handleContinue(ActionEvent event) { continueGame(); }

    @FXML
    void handleDiscard(ActionEvent event) { discardCard(); }

    @FXML
    void handlePlay(ActionEvent event) { playCard(); }



	
	Game game;
	VisibleCard topCard;
	SimpleBooleanProperty gameOver;
	SimpleBooleanProperty deckEmpty;
	SimpleIntegerProperty currentPlayer;
	VisibleCard currSelectedCard;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
		betSuitChoice.setValue(Suit.HEARTS);
		betSuitChoice.setItems(suitList);
		betNumTricksChoice.setValue(6);
		betNumTricksChoice.setItems(trickList);
		

		game = new Game(this, "Austin", "Human Team", 500);
		
		game.getCurrRound().getBettingRound().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, 
					Boolean oldValue, Boolean newValue) {
		    	if (newValue) {
		    		betButton.setDisable(false);
		    		passBetButton.setDisable(false);
		    		betSuitChoice.setDisable(false);
		    		betNumTricksChoice.setDisable(false);
		    	} else {
		    		betButton.setDisable(true);
		    		passBetButton.setDisable(true);
		    		betSuitChoice.setDisable(true);
		    		betNumTricksChoice.setDisable(true);
		    		
		    	}
			}
    	});
		
		game.getCurrRound().getKittyRound().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, 
					Boolean oldValue, Boolean newValue) {
		    	if (newValue) {
		    		discardButton.setDisable(false);
		    	} else {
		    		discardButton.setDisable(true);
		    	}
			}
    	});
		
		game.getCurrRound().getTricksRound().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, 
					Boolean oldValue, Boolean newValue) {
		    	if (newValue) {
		    		playButton.setDisable(false);
		    	} else {
		    		playButton.setDisable(true);
		    	}
			}
    	});
		
		betSuitChoice.setDisable(true);
		betNumTricksChoice.setDisable(true);
		playButton.setDisable(true);
		discardButton.setDisable(true);
		betButton.setDisable(true);
		passBetButton.setDisable(true);
		continueButton.setDisable(false);
		
		humanTeamScore.textProperty().bind(game.getPlayerTeam().getStringScore());
		CPUTeamScore.textProperty().bind(game.getCPUTeam().getStringScore());
	    currTrumpSuit.textProperty().bind(game.getCurrRound().getTrumpSuitString());
	    currBet.textProperty().bind(game.getCurrRound().getCurrBetString());
	    currStartingSuit.textProperty().bind(game.getCurrRound().getCurrStartingSuitString());
		
	    bottomPlayerTag.textProperty().bind(game.getBottomPlayer().getNameAndTeam());
	    leftPlayerTag.textProperty().bind(game.getLeftPlayer().getNameAndTeam());
	    topPlayerTag.textProperty().bind(game.getTopPlayer().getNameAndTeam());
	    rightPlayerTag.textProperty().bind(game.getRightPlayer().getNameAndTeam());

		
		
		
		gameOver = new SimpleBooleanProperty();
		deckEmpty = new SimpleBooleanProperty();
		currentPlayer = new SimpleIntegerProperty();
		
		

		updateTable();
		
	}



	private void playCard() {
		if (!game.humanPlayCard()) {
			showPopUp("Invalid card play", "You either have not selected a card or the card you have selected cannot be played.");
			return;
		}
		updateTable();
		continueGame();
	}
	
	private void continueGame() {
		if (game.CPUPlayToHumanInput()) continueButton.setDisable(true);
		else continueButton.setDisable(false);
		updateTable();
	}
	
	private void discardCard() {
		game.humanDiscardCard();
		updateTable();
		continueGame();
	}
	
	private void makeBet() {
		Suit betSuit = (Suit)betSuitChoice.getValue();
		int betNum = (int)betNumTricksChoice.getValue();
		if (!game.humanBet(betSuit, betNum)) showPopUp("Invalid Bet!", "You have inputted an invalid bet, as the current bet is greater than yours. Please enter a valid bet.");
		else continueGame();
	}
	


	private void passBet() {
		if(!game.passBet()) showPopUp("Invalid Pass!", "You cannot pass when you are the first player to bet.");
		else continueGame();
	}



	/**
	 * TODO
	 */
	private void updateTable() {
		playerCardsPane.getChildren().clear();
		bottomPlayerCardPane.getChildren().clear();
		leftPlayerCardPane.getChildren().clear();
		rightPlayerCardPane.getChildren().clear();
		topPlayerCardPane.getChildren().clear();
		for (Card k : game.getHumanCards()) {
			createVisiblePlayerCardsCard(k);
		}
		createVisibleBottomPlayedCard(game.getBottomPlayer().getCurrPlayedCard());
		createVisibleLeftPlayedCard(game.getLeftPlayer().getCurrPlayedCard());
		createVisibleRightPlayedCard(game.getRightPlayer().getCurrPlayedCard());
		createVisibleTopPlayedCard(game.getTopPlayer().getCurrPlayedCard());

		
		
	}
	
	private VisibleCard createVisibleBottomPlayedCard(Card c) {
		VisibleCard kGUI = null;
		if (c == null) kGUI = new VisibleCard("0s", false);
		else kGUI = new VisibleCard(c.toString(), c.isTrump());
		bottomPlayerCardPane.getChildren().add(kGUI);
		return kGUI;
	}
	
	private VisibleCard createVisibleTopPlayedCard(Card c) {
		VisibleCard kGUI = null;
		if (c == null) kGUI = new VisibleCard("0s", false);
		else kGUI = new VisibleCard(c.toString(), c.isTrump());
		topPlayerCardPane.getChildren().add(kGUI);
		return kGUI;
	}
	
	private VisibleCard createVisibleRightPlayedCard(Card c) {
		VisibleCard kGUI = null;
		if (c == null) kGUI = new VisibleCard("0s", false);
		else kGUI = new VisibleCard(c.toString(), c.isTrump());
		rightPlayerCardPane.getChildren().add(kGUI);
		return kGUI;
	}
	
	private VisibleCard createVisibleLeftPlayedCard(Card c) {
		VisibleCard kGUI = null;
		if (c == null) kGUI = new VisibleCard("0s", false);
		else kGUI = new VisibleCard(c.toString(), c.isTrump());
		leftPlayerCardPane.getChildren().add(kGUI);
		return kGUI;
	}


	/**
	 * Makes a visible card corresponding to a card object, and adds a listener for selecting
	 * the card
	 * @param c card object which the visible card is based on
	 * @return Visible card object
	 */
	private VisibleCard createVisiblePlayerCardsCard(Card c) {
		VisibleCard kGUI = new VisibleCard(c.toString(), c.isTrump());
		playerCardsPane.getChildren().add(kGUI);
		kGUI.setOnMouseClicked(e -> { selectCard(c, kGUI); });
		return kGUI;
	}


	/**
	 * Adds a card to the list of selected cards and changes the visible card object
	 * accordingly
	 * @param c card to be selected
	 * @param kGUI visible card object bound to the card
	 */
	public void selectCard(Card c, VisibleCard kGUI) {
		if (currSelectedCard == null) {
			kGUI.select();
			currSelectedCard = kGUI;
		} else {
			if (currSelectedCard.equals(kGUI)) {
				currSelectedCard = null;
				kGUI.select();
			} else {
				currSelectedCard.deselect();
				kGUI.select();
				currSelectedCard = kGUI;
			}
		}
		
		if (!playButton.isDisabled()) game.addSelection(c);
		if (!discardButton.isDisabled()) game.addSelection(c);
	}


	/**
	 * Checks if player is allowed to play, and changes the play button accordingly
	 */
	private void changePlayButtonOnSelection(Card c) {
		if (game.checkSelection(c)) {
			playButton.setDisable(false);
		} else { playButton.setDisable(true); }
	}
	
	public void showPopUp(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

	public void showOptionPopUp(String title, String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    game.reset();
		} else {
		    System.exit(0);
		}
		
	}
	



}
