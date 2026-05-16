package view;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DiceDisplay {
    private VBox diceBox;
    private Label diceResultLabel;
    
    public DiceDisplay(){
        diceResultLabel = new Label("Roll to start!");
        diceResultLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        diceBox = new VBox(10);
        diceBox.getChildren().add(diceResultLabel);
    }
    
    public void showResult(int result){
        diceResultLabel.setText("The dice roll resulted in: " + result);
    }
    
    public VBox getDiceBox(){
        return diceBox;
    }
}