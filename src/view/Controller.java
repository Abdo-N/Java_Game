package view;

import model.game.engine.Board;
import model.game.engine.Game;
import model.game.engine.cards.Card;
import model.game.engine.cells.CardCell;
import model.game.engine.exceptions.InvalidMoveException;
import model.game.engine.exceptions.OutOfEnergyException;
import model.game.engine.monsters.Monster;
import view.App;

public class Controller {

    private Game game;
    private App app;
    private int turnNumber = 1;

    public Controller(Game game, App app){
        this.game = game;
        this.app = app;
    }

    public void handlePowerUp(){
        try {
            game.usePowerup();
        } catch (OutOfEnergyException e) {
            app.showMessage("Not enough energy to use powerup!");
        }
    }

    public void handleRollDice(){
        Monster currentBefore = game.getCurrent();
        boolean wasFrozen = currentBefore.isFrozen();

        try {
            game.playTurn();

            if(wasFrozen){
                app.showFrozen(currentBefore.getName());
            } else {
                app.showDiceResult(game.getLastRoll());

                Card card = Board.getLastDrawnCard();
                if(card != null){
                    app.showCardPopup(card.getName(), card.getDescription());
                    Board.setLastDrawnCard(null);
                }
            }

            turnNumber++;
            app.onTurnEnd(
                turnNumber,
                game.getPlayer().getName(),
                game.getOpponent().getName()
            );

            Monster winner = game.getWinner();
            if(winner != null){
                app.showWinner(winner);
                return;
            }

            app.promptNextTurn();

        } catch (InvalidMoveException e) {
            app.showMessage(e.getMessage());
            app.promptNextTurn();
        }
    }
}