package view;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import model.game.engine.Role;

public class StartScreen extends BorderPane {

    Font titleFont = Font.loadFont("file:assets/fonts/PressStart2P.ttf", 48);
    Font subtitleFont = Font.loadFont("file:assets/fonts/PressStart2P.ttf", 20);
    Font quoteFont = Font.loadFont("file:assets/fonts/PressStart2P.ttf", 8);
    Font buttonFont = Font.loadFont("file:assets/fonts/PressStart2P.ttf", 16);
    private VBox selectedCard = null; 
    private Role selectedRole = null;
    private Button startButton;
    private App app;

    public StartScreen(App app) {
        this.app = app;
        this.setTop(createTopSection());      // title
        this.setCenter(createCenterSection()); // cards
        this.setBottom(createBottomSection()); // button
        this.setStyle("-fx-background-image: url('file:assets/images/StartScreen.png'); -fx-background-size: cover;");
    }
    
    
    private VBox createTopSection() {

        Label title = new Label("DooR DasH");
        title.setFont(titleFont);
        title.setStyle("-fx-text-fill: white; -fx-effect: dropshadow(gaussian, #874885, 1, 0.2, 2.5, 2.5);");

        Label subTitle1 = new Label("Scare vs Laugh");
        subTitle1.setFont(subtitleFont);
        subTitle1.setStyle("-fx-text-fill: lightgray;");
        subTitle1.setStyle("-fx-text-fill: lightgray; -fx-effect: dropshadow(gaussian, #585858, 1, 0.2, 2, 2);");
        
        Label subTitle2 = new Label("Touchdown");
        subTitle2.setFont(subtitleFont);
        subTitle2.setStyle("-fx-text-fill: lightgray;");
        subTitle2.setStyle("-fx-text-fill: lightgray; -fx-effect: dropshadow(gaussian, #585858, 1, 0.2, 2, 2);");

        VBox top = new VBox(10);
        top.setStyle("-fx-alignment: center; -fx-padding: 40 0 0 0;");
        top.getChildren().addAll(title, subTitle1, subTitle2);
        
        return top;
    }
    
    private HBox createCenterSection() {
        VBox scarerCard = createCard("James_P__Sullivan.png", "SCARER", "Sullivan", "We scare because we care");
        VBox laugherCard = createCard("Mike_wazowski.png", "LAUGHER", "Mike", "We laugh that's our path");
        
        Label vsLabel = new Label("VS");
        vsLabel.setFont(subtitleFont);
        vsLabel.setStyle("-fx-text-fill: white; -fx-effect: dropshadow(gaussian, #000000, 1, 0.2, 2, 2);");

        HBox center = new HBox(90);
        center.setStyle("-fx-alignment: center;");
        center.getChildren().addAll(scarerCard,vsLabel,laugherCard);
        return center;
    }
    
    private VBox createBottomSection() {

        Label startLabel = new Label("START GAME");
        startLabel.setFont(buttonFont);
        startLabel.setStyle("-fx-text-fill: white; -fx-effect: dropshadow(gaussian, #000000, 1, 0.3, 2.5, 2.5);");


        startButton = new Button();
        startButton.setGraphic(startLabel);
        startButton.setStyle("-fx-text-fill: white; -fx-padding: 15 40 15 40; -fx-background-color: #874885; -fx-border-color: #ffffff; -fx-border-width: 3; -fx-effect: dropshadow(gaussian, #582d57, 1, 0.2, 3, 3);");
        startButton.setDisable(true);
        startButton.setOpacity(0);
        
        startButton.setOnMouseEntered(e -> {
        startButton.setStyle("-fx-text-fill: white; -fx-padding: 15 40 15 40; -fx-background-color: #8c508c; -fx-border-color: #ffffff; -fx-border-width: 3;");
        });

        startButton.setOnMouseExited(e -> {
        startButton.setStyle("-fx-text-fill: white; -fx-padding: 15 40 15 40; -fx-background-color: #874885; -fx-border-color: #ffffff; -fx-border-width: 3;");
        });

        startButton.setOnAction(e -> {
            if (selectedRole != null) {
                startGame(selectedRole);
            }
        });
    
        VBox bottom = new VBox(startButton);
        bottom.setStyle("-fx-alignment: center; -fx-padding: 0 0 100 0;");
        return bottom;
    }

    private void startGame(Role role) {
        // TODO: Create Game instance, switch to GameBoard screen
        System.out.println("Game started with role: " + role);
        try {
            app.startGame(role);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VBox createCard(String imagePath, String roleName, String monsterName, String quote) {
        // Image
        Image image = new Image("file:assets/images/" + imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        // Labels
        Label roleLabel = new Label(roleName);
        roleLabel.setFont(subtitleFont);
        roleLabel.setStyle("-fx-text-fill: white;");
        roleLabel.setStyle("-fx-text-fill: white; -fx-effect: dropshadow(gaussian, #000000, 1, 0.2, 2, 2);");

        Label quoteLabel = new Label(quote);
        quoteLabel.setFont(quoteFont);
        quoteLabel.setStyle("-fx-text-fill: lightgray;");


        VBox card = new VBox(10);
        card.setPrefSize(270, 270);
        card.setMinSize(270, 270);
        card.setMaxSize(270, 270);

        card.setStyle("-fx-border-color: #414152; -fx-border-width: 3; -fx-padding: 30; -fx-alignment: center; -fx-background-color: #31313f;");

        card.setOnMouseClicked(e -> {
            // Deselect previous card
            if (selectedCard != null) {
                selectedCard.setStyle("-fx-border-color: #414152; -fx-border-width: 3; -fx-padding: 30; -fx-alignment: center; -fx-background-color: #31313f;");
            }
            // Select this card
            selectedCard = card;
            selectedRole = roleName.equals("SCARER") ? Role.SCARER : Role.LAUGHER;
            card.setStyle("-fx-border-color: #824781; -fx-border-width: 3; -fx-padding: 30; -fx-alignment: center; -fx-background-color: #31313f;");
            
            // Show start button
            startButton.setDisable(false);
            startButton.setOpacity(1);
        });

        card.setOnMouseEntered(e -> {
            if (selectedCard != card) {
                card.setStyle("-fx-border-color: #9b59b6; -fx-border-width: 3; -fx-padding: 30; -fx-alignment: center; -fx-background-color: #2a2a3e;");
            }
        });

        card.setOnMouseExited(e -> {
            if (selectedCard != card) {
                card.setStyle("-fx-border-color: #414152; -fx-border-width: 3; -fx-padding: 30; -fx-alignment: center; -fx-background-color: #31313f;");
            }
        });

        card.getChildren().addAll(imageView, roleLabel, quoteLabel);


        return card;
    }
}
