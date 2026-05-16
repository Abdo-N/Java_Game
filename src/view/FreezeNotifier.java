package view;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FreezeNotifier {
    public void show(String monsterName){
        Stage dialog = new Stage();
        dialog.setTitle("Turn Skipped!");
        Label header = new Label(monsterName + " is frozen!");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label body = new Label(monsterName + "'s turn has been skipped due to the Freeze effect.");
        VBox box = new VBox(10, header, body);
        box.setStyle("-fx-padding: 20; -fx-alignment: center;");
        dialog.setScene(new Scene(box, 350, 150));
        dialog.show();
    }
}