package view;

import controller.interfaces.GameEventListener;
import model.game.engine.monsters.Monster;
import model.game.engine.cards.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.game.engine.monsters.Dasher;
import model.game.engine.monsters.MultiTasker;
import java.io.File;

public class MonsterPanelController implements GameEventListener {
private static final int PANEL_WIDTH = 280;
private static final int PANEL_HEIGHT = 720;
private static final int STAT_BOX_HEIGHT = 45;

// Badge colors
    private static final Color SHIELD_COLOR = Color.web("#554008");
    private static final Color CONFUSION_COLOR = Color.web("#0d0ac8");
    private static final Color FREEZE_COLOR = Color.web("#00DDFF");
    private static final Color MOMENTUM_COLOR = Color.web("#dfc21e");
    private static final Color FOCUS_COLOR = Color.web("#6a5d6f");

private Monster monster;
private StackPane panelContainer;
private ImageView panelBackground;
private Label monsterName;
private Label monsterType;
private Label roleLabel;
private Label energyLabel;
private Label energyChange;
private Label positionLabel;
private HBox statusEffectsContainer;
private String panelImageFilename;

public MonsterPanelController(Monster monster, String panelImageFilename){
  this.monster = monster;
  this.panelImageFilename = panelImageFilename;
  initializePanel();
}

private void initializePanel(){
    panelContainer = new StackPane();
    panelContainer.setPrefSize(PANEL_WIDTH, PANEL_HEIGHT);
    panelContainer.setStyle("-fx-border-color: #333333; -fx-border-width: 2;");

     loadPanelBackground();

     VBox contentBox = createContentLayout();
     
     panelContainer.getChildren().addAll(panelBackground,contentBox);
}




private void loadPanelBackground() {
    try {
        // Try to load from filesystem directly
        String imagePath = "assets/fonts/images/panels/" + panelImageFilename;
        File imageFile = new File(imagePath);
        
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            panelBackground = new ImageView(image);
            panelBackground.setFitWidth(PANEL_WIDTH);
            panelBackground.setFitHeight(PANEL_HEIGHT);
            panelBackground.setPreserveRatio(false);
        } else {
            System.err.println("Image not found: " + imagePath);
            createFallback();
        }
    } catch (Exception e) {
        System.err.println("Error loading panel background: " + e.getMessage());
        createFallback();
    }
}

private void createFallback() {
    Region fallback = new Region();
    fallback.setPrefSize(PANEL_WIDTH, PANEL_HEIGHT);
    fallback.setStyle("-fx-background-color: #2A2A2A;");
    panelBackground = new ImageView();
}

  private VBox createContentLayout() {
        VBox contentBox = new VBox(10);
        contentBox.setPadding(new Insets(15));
        contentBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
        
        VBox headerSection = createHeaderSection();
        
        VBox statsSection = createStatsSection();
        
        VBox effectsSection = createEffectsSection();
        
        contentBox.getChildren().addAll(
            headerSection,
            new Region() {{ setPrefHeight(10); }}, 
            statsSection,
            new Region() {{ setPrefHeight(10); }}, 
            effectsSection
        );
        
        return contentBox;
    }
 
     private VBox createEffectsSection() {
        VBox effectsBox = new VBox(8);
        
        Label effectsTitle = new Label("ACTIVE EFFECTS");
        effectsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        effectsTitle.setTextFill(Color.GRAY);
        
        statusEffectsContainer = new HBox(8);
        statusEffectsContainer.setStyle("-fx-padding: 5; -fx-background-color: rgba(40, 40, 40, 0.8); -fx-border-radius: 5;");
        statusEffectsContainer.setSpacing(8);
        
        effectsBox.getChildren().addAll(effectsTitle, statusEffectsContainer);
        
        // Initialize with current effects
        updateStatusEffects();
        
        return effectsBox;
    }

    private VBox createHeaderSection() {
        VBox header = new VBox(5);
        header.setStyle("-fx-background-color: rgba(0, 51, 170, 0.8); -fx-padding: 10; -fx-border-radius: 5;");

        monsterName = new Label(monster.getName());
        monsterName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        monsterName.setTextFill(Color.WHITE);

        monsterType = new Label(monster.getRole() + " • " + monster.getRole());
        monsterType.setFont(Font.font("Arial", 12));
        monsterType.setTextFill(Color.LIGHTGRAY);
        
        // Current role (with confusion indicator)
        roleLabel = new Label("Role: " + monster.getRole());
        roleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        roleLabel.setTextFill(Color.WHITE);
        
        header.getChildren().addAll(monsterName, monsterType, roleLabel);
        return header; 
    }  

  // Create stats section (energy and position)
private VBox createStatsSection() {
    VBox statsBox = new VBox(8);
    
    // Energy row
    HBox energyRow = new HBox(10);
    energyRow.setAlignment(Pos.CENTER_LEFT);
    energyRow.setPadding(new Insets(8));
    energyRow.setStyle("-fx-background-color: rgba(80, 80, 80, 0.8); -fx-border-radius: 5;");
    
    Label energyLabelText = new Label("Energy: ");
    energyLabelText.setFont(Font.font("Arial", 12));
    energyLabelText.setTextFill(Color.LIGHTGRAY);
    
    energyLabel = new Label(String.valueOf(monster.getEnergy()));
    energyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    energyLabel.setTextFill(Color.web("#00FF00"));
    
    energyChange = new Label("");
    energyChange.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    energyChange.setTextFill(Color.web("#00FF00"));
    
    energyRow.getChildren().addAll(energyLabelText, energyLabel, energyChange);
    
    // Position row
    HBox positionRow = new HBox(10);
    positionRow.setAlignment(Pos.CENTER_LEFT);
    positionRow.setPadding(new Insets(8));
    positionRow.setStyle("-fx-background-color: rgba(80, 80, 80, 0.8); -fx-border-radius: 5;");
    
    Label positionLabelText = new Label("Position: ");
    positionLabelText.setFont(Font.font("Arial", 12));
    positionLabelText.setTextFill(Color.LIGHTGRAY);
    
    positionLabel = new Label("Cell " + monster.getPosition());
    positionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    positionLabel.setTextFill(Color.web("#00FF00"));
    
    positionRow.getChildren().addAll(positionLabelText, positionLabel);
    
    statsBox.getChildren().addAll(energyRow, positionRow);
    return statsBox;
}


     public void onEnergyChanged(Monster monster, int deltaEnergy) {
        if (!this.monster.equals(monster)) return;
        
        int newEnergy = monster.getEnergy();
        energyLabel.setText(String.valueOf(newEnergy));
        
        // Show +/- indicator
        if (deltaEnergy > 0) {
            energyChange.setText(" (+" + deltaEnergy + ")");
            energyChange.setTextFill(Color.web("#00FF00"));
        } else {
            energyChange.setText(" (" + deltaEnergy + ")");
            energyChange.setTextFill(Color.web("#FF0000"));
        }
        
        // Animate the energy label
        animatePulse(energyLabel);
    }

    @Override
    public void onStatusEffectChanged(Monster monster){
        if(!this.monster.equals(monster)) return;
         
        statusEffectsContainer.getChildren().clear();

        updateStatusEffects();

    }

      public void onMonsterMoved(Monster monster, int newCell) {
        if (!this.monster.equals(monster)) return;
        
        positionLabel.setText("Cell " + newCell);
    }


 @Override
    public void onCardDrawn(Monster monster, Card card) { }
    
    @Override
    public void onTurnStarted(Monster current) { }
    
    @Override
    public void onTurnEnded(Monster current) { }
    
    @Override
    public void onMonsterFrozen(Monster monster) { }
    
    @Override
    public void onInvalidAction(String reason) { }
    
    @Override
    public void onGameWon(Monster winner) { }
    
    @Override
    public void onCellEffect(Monster monster, int oldCell, int newCell) { }
    


private void animatePulse(Label label) {
     ScaleTransition pulse = new ScaleTransition(Duration.millis(300), label);
     pulse.setFromX(1.0);
     pulse.setToX(1.2);
     pulse.setCycleCount(2);
 pulse.setAutoReverse(true);
        pulse.play();
    }


// Update status effects display
    private void updateStatusEffects() {
        statusEffectsContainer.getChildren().clear();
        
        boolean hasEffects = false;
        
        // Check Shield
        if (monster.isShielded()) {
            HBox shieldBadge = createEffectBadge("Shield", 1);
            statusEffectsContainer.getChildren().add(shieldBadge);
            hasEffects = true;
        }
        
        // Check Confusion
        if (monster.isConfused()) {
            int turnsRemaining = monster.getConfusionTurns();
            HBox confusionBadge = createEffectBadge("Confusion", turnsRemaining);
            statusEffectsContainer.getChildren().add(confusionBadge);
            hasEffects = true;
        }
        
        // Check Frozen
        if (monster.isFrozen()) {
            HBox frozenBadge = createEffectBadge("Freeze", 1);
            statusEffectsContainer.getChildren().add(frozenBadge);
            hasEffects = true;
        }
        
        // Check Momentum Rush (for Dasher)
        if (monster instanceof Dasher) {
            Dasher dasher = (Dasher) monster;
            if (dasher.getMomentumTurns() > 0) {
                HBox momentumBadge = createEffectBadge("Momentum", dasher.getMomentumTurns());
                statusEffectsContainer.getChildren().add(momentumBadge);
                hasEffects = true;
            }
        }
        
        // Check Focus Mode (for MultiTasker)
        if (monster instanceof MultiTasker) {
            MultiTasker multitasker = (MultiTasker) monster;
            if (multitasker.getNormalSpeedTurns() > 0) {
                HBox focusBadge = createEffectBadge("Focus", multitasker.getNormalSpeedTurns());
                statusEffectsContainer.getChildren().add(focusBadge);
                hasEffects = true;
            }
        }
        
        // If no effects, show "None"
        if (!hasEffects) {
            Label noEffects = new Label("None");
            noEffects.setFont(Font.font("Arial", 11));
            noEffects.setTextFill(Color.GRAY);
            statusEffectsContainer.getChildren().add(noEffects);
        }
    }
   


// Create a single status effect badge
    private HBox createEffectBadge(String effectName, int turnsRemaining) {
        HBox badge = new HBox(6);
        badge.setAlignment(Pos.CENTER_LEFT);
        badge.setPadding(new Insets(5, 10, 5, 10));
        badge.setStyle("-fx-border-radius: 5; -fx-border-width: 1; -fx-border-color: #000000;");
        
        // Determine color based on effect type
        Color badgeColor = getBadgeColor(effectName);
        badge.setStyle(badge.getStyle() + " -fx-background-color: " + colorToHex(badgeColor) + ";");
        
        // Load icon image (20×20)
        ImageView icon = loadEffectIcon(effectName);
        if (icon != null) {
            icon.setFitWidth(16);
            icon.setFitHeight(16);
            badge.getChildren().add(icon);
        }
        
        // Effect name + duration label
        Label effectLabel = new Label(effectName + " (" + turnsRemaining + "t)");
        effectLabel.setFont(Font.font("Arial", 11));
        effectLabel.setTextFill(Color.WHITE);
        badge.getChildren().add(effectLabel);
        
        return badge;
    }
    
    // Get color based on effect name
    private Color getBadgeColor(String effectName) {
        switch (effectName.toLowerCase()) {
            case "shield":
                return SHIELD_COLOR;
            case "confusion":
                return CONFUSION_COLOR;
            case "freeze":
                return FREEZE_COLOR;
            case "momentum rush":
            case "momentum":
                return MOMENTUM_COLOR;
            case "focus mode":
            case "focus":
                return FOCUS_COLOR;
            default:
                return Color.GRAY;
        }
    }
    
    // Load effect icon image
    private ImageView loadEffectIcon(String effectName) {
        try {
            String iconFile = null;
            switch (effectName.toLowerCase()) {
                case "shield":
                    iconFile = "icon_shield.png";
                    break;
                case "confusion":
                    iconFile = "icon_confusion.png";
                    break;
                case "freeze":
                    iconFile = "icon_freeze.png";
                    break;
                case "momentum rush":
                case "momentum":
                    iconFile = "icon_momentum.png";
                    break;
                case "focus mode":
                case "focus":
                    iconFile = "icon_focus.png";
                    break;
                default:
                    return null;
            }
            
             if (iconFile != null) {
            File imageFile = new File("assets/fonts/images/icons/" + iconFile);
            if (imageFile.exists()) {
                Image icon = new Image(imageFile.toURI().toString());
                return new ImageView(icon);
            }
        }
    } catch (Exception e) {
        System.err.println("Error loading icon for " + effectName + ": " + e.getMessage());
    }
    return null;
    }
    
    //  Convert Color to Hex string
    private String colorToHex(Color color) {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }

    public StackPane getPanel() {
      return panelContainer;
    }



}