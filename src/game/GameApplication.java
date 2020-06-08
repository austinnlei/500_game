package game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class GameApplication extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("500_game.fxml"));
			
			final Pane root = (Pane)ldr.load();
			final Scene gameScene = new Scene(root);
			
			
			
			gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle (KeyEvent event){
					if (event.getCode() == KeyCode.F1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Game instructions");
						alert.setHeaderText(null);
						try {
							alert.setContentText(new String(Files.readAllBytes(Paths.get("src/game/Instructions.txt"))));
						} catch (IOException e) {
							alert.setContentText("ERROR: instructions not found");
						}

						alert.showAndWait();
					}
				}
			});
	        // Start scene
	        HBox startButtons = new HBox();
	        startButtons.setSpacing(10.0);
	        Button startToGame = new Button("Start");
	        startToGame.setAlignment(Pos.BOTTOM_CENTER);
	        startButtons.setAlignment(Pos.CENTER);
	        startToGame.setOnAction(e -> primaryStage.setScene(gameScene));
	        startButtons.getChildren().addAll(startToGame);
	        GridPane startLayout = new GridPane();
	        startLayout.setAlignment(Pos.CENTER);
	        startLayout.setHgap(10);
	        startLayout.setVgap(12);
	        
	        startLayout.add(startButtons, 0, 2, 2, 1);
	        
	        
	        final Scene start = new Scene(startLayout, 1000, 800);
	        
			primaryStage.setScene(start);
			primaryStage.setTitle("500");
			primaryStage.setResizable(false);


		} catch(Exception e) {
			e.printStackTrace();
		}
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
