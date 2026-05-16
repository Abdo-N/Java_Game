package view;

import java.io.IOException;

import model.game.engine.Game;
import model.game.engine.Role;
import model.game.engine.monsters.Monster;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.DiceDisplay;

public class App extends Application {
	
	private DiceDisplay diceDisplay = new DiceDisplay();
	private CardPopup cardPopup = new CardPopup();
	private FreezeNotifier freezeNotifier = new FreezeNotifier();
	private TurnTracker turnTracker = new TurnTracker();
	private Controller controller;
	private PreTurnChoices preTurnChoices;
	private Game game;
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
	    this.primaryStage = primaryStage;
	    primaryStage.setTitle("DoorDash - JavaFX Window");
	    StartScreen startScreen = new StartScreen(this);
	    Scene scene = new Scene(startScreen, 800, 600);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}
	private Parent buildGameLayout() {
	    BorderPane layout = new BorderPane();
	    layout.setTop(turnTracker.getTrackerBox());
	    layout.setBottom(preTurnChoices.getButtonBox());
	    layout.setRight(diceDisplay.getDiceBox());
	    return layout;
	}
    public void startGame(Role role) throws IOException {
        game = new Game(role);
        controller = new Controller(game, this);
        preTurnChoices = new PreTurnChoices(controller);
        // switch to game screen
        Scene gameScene = new Scene(buildGameLayout(), 800, 600);
        primaryStage.setScene(gameScene);
        turnTracker.updateTracker(1, game.getPlayer().getName(), game.getOpponent().getName());
        preTurnChoices.promptPreTurnChoices();
    }
    
    public void showDiceResult(int result){
        diceDisplay.showResult(result);
    }

    public void showCardPopup(String name, String description){
        cardPopup.show(name, description);
    }

    public void showFrozen(String monsterName){
        freezeNotifier.show(monsterName);
    }

    public void showMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    public void onTurnEnd(int turnNumber, String currentPlayer, String opponent){
        turnTracker.updateTracker(turnNumber, currentPlayer, opponent);
        // call board and monster panel updates here
    }

    public void promptNextTurn(){
        preTurnChoices.promptPreTurnChoices();
    }

    public void showWinner(Monster winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(winner.getName() + " wins!");
        alert.setContentText("Role: " + winner.getRole() + "\nFinal Energy: " + winner.getEnergy());
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}