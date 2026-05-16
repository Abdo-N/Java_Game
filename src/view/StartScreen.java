package view;

import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartScreen extends BorderPane {

    public StartScreen() {
        this.setTop(createTopSection());      // title
        this.setCenter(createCenterSection()); // cards
        this.setBottom(createBottomSection()); // button
    }
    
    private VBox createTopSection() {
    Font titleFont = Font.loadFont("file:assets/fonts/PressStart2P.ttf", 48);
    Font subtitleFont = Font.loadFont("file:assets/fonts/PressStart2P.ttf", 24);

    Label title = new Label("DooR DasH");
    title.setFont(titleFont);

    Label subTitle1 = new Label("Scare vs Laugh");
    subTitle1.setFont(subtitleFont);

    Label subTitle2 = new Label("Touchdown");
    subTitle2.setFont(subtitleFont);

    VBox top = new VBox(10);
    top.setStyle("-fx-alignment: center; -fx-padding: 40 0 0 0;");
    top.getChildren().addAll(title, subTitle1, subTitle2);
    
    return top;
}
    
    private HBox createCenterSection() {
        Button scarerCard = new Button("SCARER");
        Button laugherCard = new Button("LAUGHER");
        HBox center = new HBox(20, scarerCard, laugherCard);
        center.setStyle("-fx-alignment: center; -fx-padding: 20;");
        return center;
    }
    
    private VBox createBottomSection() {
        Button startButton = new Button("START GAME");
        VBox bottom = new VBox(startButton);
        return bottom;
    }
}
