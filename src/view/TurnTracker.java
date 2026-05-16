package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TurnTracker {
	
	private Label turnLabel = new Label("Turn: 1");
	private Label currentLabel = new Label("Current: ");
	private Label opponentLabel = new Label("Opponent: ");
	private VBox trackerBox;
	
	public TurnTracker() {
	    turnLabel = new Label("Turn: 1");
	    currentLabel = new Label("Current: ");
	    opponentLabel = new Label("Opponent: ");
	    
	    turnLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
	    currentLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
	    opponentLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
	    
	    trackerBox = new VBox();
	    trackerBox.getChildren().addAll(turnLabel, currentLabel, opponentLabel);
	}
	
	public VBox getTrackerBox() {
        return trackerBox;
    }
	
	public void updateTracker(int turn, String currentPlayer, String opponent) {
	    turnLabel.setText("Turn: " + turn);
	    currentLabel.setText("Current: " + currentPlayer);
	    opponentLabel.setText("Opponent: " + opponent);
	}

}
