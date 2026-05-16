package view;
import javafx.scene.control.Alert;

public class CardPopup {
    public void show(String cardName, String cardDescription){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Card Drawn!");
        alert.setHeaderText(cardName);
        alert.setContentText(cardDescription);
        alert.show();
    }
}