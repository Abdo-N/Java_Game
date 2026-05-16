package view;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CardPopup {
    public void show(String cardName, String cardDescription){
        Stage dialog = new Stage();
        dialog.setTitle("Card Drawn!");
        Label nameLabel = new Label(cardName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label descLabel = new Label(cardDescription);
        VBox box = new VBox(10, nameLabel, descLabel);
        box.setStyle("-fx-padding: 20; -fx-alignment: center;");
        dialog.setScene(new Scene(box, 300, 150));
        dialog.show();
    }
}