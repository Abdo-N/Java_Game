package view;
import javafx.scene.control.Alert;

public class FreezeNotifier {
    public void show(String monsterName){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Turn Skipped!");
        alert.setHeaderText(monsterName + " is frozen!");
        alert.setContentText(monsterName + "'s turn has been skipped due to the Freeze effect.");
        alert.show();
    }
}