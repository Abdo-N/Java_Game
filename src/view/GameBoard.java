package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;


import model.game.engine.*;
import model.game.engine.cells.*;
import model.game.engine.monsters.Monster;

public class GameBoard extends BorderPane {
    
    Font CellFont = Font.loadFont("file:assets/fonts/PressStart2P.ttf", 10);

    public GameBoard(App app, Game game) {
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #1a1a2e; -fx-gap: 2;");
        
        Cell[][] boardCells = game.getBoard().getBoardCells();

        //Build all cells
        for (int i = 0; i < 100; i++) {
                int[] pos = indexToRowCol(i);
                Cell cell = boardCells[pos[0]][pos[1]];
                
                //Each node is now a StackPane to allow for multiple labels (index and energy)
                StackPane cellNode = new StackPane();
                cellNode.setPrefSize(60, 60);
                cellNode.setStyle("-fx-background-color: " + getCellColor(cell) + "; -fx-border-color: black;");

                // Add index label to the top-left corner
                Label indexLabel = new Label(String.valueOf(i));
                indexLabel.setFont(new Font("Arial", 10));
                indexLabel.setAlignment(Pos.TOP_LEFT);

                // If it's a DoorCell, also add the energy label to the bottom-right corner
                if (cell instanceof DoorCell) {
                    DoorCell door = (DoorCell) cell;
                    Label energyLabel = new Label(String.valueOf(door.getEnergy()));
                    energyLabel.setFont(new Font("Arial", 9));
                    energyLabel.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 2;");
                    StackPane.setAlignment(energyLabel, Pos.CENTER);
                    StackPane.setAlignment(indexLabel, Pos.TOP_LEFT);
                    cellNode.getChildren().addAll(indexLabel, energyLabel);
                } else {
                    // For non-DoorCells, just add the index label
                    StackPane.setAlignment(indexLabel, Pos.TOP_LEFT);
                    cellNode.getChildren().add(indexLabel);
                }

                if (i == 0) {
                    cellNode.getChildren().add(createMonsterTokens(game.getPlayer(), game.getOpponent()));
                } else {
                    if (game.getPlayer().getPosition() == i) {
                        cellNode.getChildren().add(createMonsterToken(game.getPlayer()));
                    }
                    if (game.getOpponent().getPosition() == i) {
                        cellNode.getChildren().add(createMonsterToken(game.getOpponent()));
                    }
                }

                // Convert row to match the visual layout (0 at bottom, 9 at top)
                int row = 9 - pos[0];
                grid.add(cellNode, pos[1], row);
            }

        this.setCenter(grid);


    }

    //Helper methods to create monster tokens (separate for player and opponent, and combined for when they start on the same cell)
    private Node createMonsterToken(Monster monster) {
        Circle token = new Circle(10);
        token.setFill(monster.getRole() == Role.SCARER 
        ? javafx.scene.paint.Color.RED 
        : javafx.scene.paint.Color.GREEN);
        return token;
    }

    // Create a combined token for both monsters when they start on the same cell
    private Node createMonsterTokens(Monster player, Monster opponent) {
        HBox tokenBox = new HBox(5);
        tokenBox.setAlignment(Pos.CENTER);
        tokenBox.getChildren().addAll(createMonsterToken(player), createMonsterToken(opponent));
        return tokenBox;
    }

    private int[] indexToRowCol(int index) {
        int cols = 10;
        int row = index / cols;
        int col = index % cols;
        
        if (row % 2 == 1)
            col = cols - 1 - col;
        
        return new int[]{row, col};
    }

    private String getCellColor(Cell cell) {
        if (cell instanceof DoorCell) {
            DoorCell door = (DoorCell) cell;
            return door.getRole().toString().equals("SCARER") ? "#ff0000" : "#0000ff";
        } else if (cell instanceof CardCell) {
            return "#ff8800";
        } else if (cell instanceof ConveyorBelt) {
            return "#00ff00";
        } else if (cell instanceof ContaminationSock) {
            return "#ff00ff";
        } else if (cell instanceof MonsterCell) {
            return "#aa00ff";
        } else {
            return "#ffff00";
        }
    }

   

}
