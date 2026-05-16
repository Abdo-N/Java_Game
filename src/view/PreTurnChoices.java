package view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PreTurnChoices {
	
	private HBox buttonBox;
	private Button rollButton;
	private Button powerupButton;
	private Button skipButton;
	private Controller controller;
	
	public PreTurnChoices(Controller controller){
		this.controller=controller;
	    rollButton = new Button("Roll Dice");
	    powerupButton = new Button("Activate Powerup");
	    skipButton = new Button("Skip Powerup");
	    buttonBox = new HBox(10);
	    buttonBox.getChildren().addAll(powerupButton, skipButton, rollButton);
	    buttonBox.setVisible(false);
	    

	    powerupButton.setOnAction(e -> {
	    	controller.handlePowerUp();
	        rollButton.setDisable(false);
	        powerupButton.setDisable(true);
	        skipButton.setDisable(true);
	    });
	    skipButton.setOnAction(e -> {
	        rollButton.setDisable(false);
	        powerupButton.setDisable(true);
	        skipButton.setDisable(true);
	    });
	    rollButton.setOnAction(e -> {
	        buttonBox.setVisible(false);
	        controller.handleRollDice();
	    });
	}

	public void promptPreTurnChoices(){
	    rollButton.setDisable(true);
	    powerupButton.setDisable(false);
	    skipButton.setDisable(false);
	    buttonBox.setVisible(true);
	}

	public HBox getButtonBox(){
	    return buttonBox;
	}
}
