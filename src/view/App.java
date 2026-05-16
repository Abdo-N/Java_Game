package view;

import java.io.IOException;

import model.game.engine.Game;
import model.game.engine.Role;
import model.game.engine.monsters.Monster;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.DiceDisplay;
import model.game.engine.Role;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.game.engine.monsters.Monster;
import model.game.engine.monsters.Dasher;
import model.game.engine.monsters.Dynamo;
import view.MonsterPanelController;

public class App extends Application {
	
	private DiceDisplay diceDisplay = new DiceDisplay();
	private CardPopup cardPopup = new CardPopup();
	private FreezeNotifier freezeNotifier = new FreezeNotifier();
	private TurnTracker turnTracker = new TurnTracker();
	private Controller controller;
	private PreTurnChoices preTurnChoices;
	private Game game;
	private Stage primaryStage;
    private MonsterPanelController leftPanel;
    private MonsterPanelController rightPanel;
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
    HBox layout = new HBox(10);
    
    leftPanel = new MonsterPanelController(game.getPlayer(), "panel_bg1.png");
    layout.getChildren().add(leftPanel.getPanel());
    
    VBox center = new VBox();
    center.setPrefWidth(700);
    center.setStyle("-fx-background-color: #333333;");
    layout.getChildren().add(center);
    
    rightPanel = new MonsterPanelController(game.getOpponent(), "panel_bg2.png");
    layout.getChildren().add(rightPanel.getPanel());
    
    //  person 1 needs to do addlistenerRegister panels as event listeners
    //controller.addListener(leftPanel);
    //controller.addListener(rightPanel);
    
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
        Stage dialog = new Stage();
        Label label = new Label(message);
        VBox box = new VBox(label);
        box.setStyle("-fx-padding: 20; -fx-alignment: center;");
        dialog.setScene(new Scene(box, 300, 100));
        dialog.show();
    }

    public void onTurnEnd(int turnNumber, String currentPlayer, String opponent){
        turnTracker.updateTracker(turnNumber, currentPlayer, opponent);
        // call board and monster panel updates here
    }

    public void promptNextTurn(){
        preTurnChoices.promptPreTurnChoices();
    }

    public void showWinner(Monster winner){
        Stage dialog = new Stage();
        Label label = new Label(winner.getName() + " wins!\nRole: " + winner.getRole() + "\nFinal Energy: " + winner.getEnergy());
        VBox box = new VBox(label);
        box.setStyle("-fx-padding: 20; -fx-alignment: center;");
        dialog.setScene(new Scene(box, 300, 150));
        dialog.show();
      
        // Create mock monsters for testing (replace with actual game monsters)
        Monster monster1 = new Dasher("Monster 1", "Test", Role.SCARER, 100);
        Monster monster2 = new Dynamo("Monster 2", "Test", Role.LAUGHER, 100);

        // Create panel controllers
        MonsterPanelController leftPanel = new MonsterPanelController(monster1, "panel_bg1.png");
        MonsterPanelController rightPanel = new MonsterPanelController(monster2, "panel_bg2.png");

        // Get the actual UI nodes
        StackPane leftPanelUI = leftPanel.getPanel();
        StackPane rightPanelUI = rightPanel.getPanel();

        // Create a layout with both panels
        HBox gameLayout = new HBox();
        gameLayout.getChildren().addAll(leftPanelUI, rightPanelUI);

        Scene gameScene = new Scene(gameLayout, 1280, 720);

        primaryStage.setScene(gameScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}