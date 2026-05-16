package view;

import java.util.ArrayList;
import controller.interfaces.GameEventListener;

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
    private ArrayList<GameEventListener> listeners = new ArrayList<>();
    private boolean previousPlayerShielded = false;
    private boolean previousOpponentShielded = false;

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
            // Store shield state before turn
            boolean playerShieldedBefore = game.getPlayer().isShielded();
            boolean opponentShieldedBefore = game.getOpponent().isShielded();

            game.playTurn();

            // Check if shields blocked damage
            if (playerShieldedBefore && !game.getPlayer().isShielded()) {
                for (GameEventListener listener : listeners) {
                    listener.onShieldBlocked(game.getPlayer());
                }
            }
            if (opponentShieldedBefore && !game.getOpponent().isShielded()) {
                for (GameEventListener listener : listeners) {
                    listener.onShieldBlocked(game.getOpponent());
                }
            }

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

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    private void notifyEnergyChanged(Monster monster, int delta) {
        for (GameEventListener listener : listeners) {
            listener.onEnergyChanged(monster, delta);
        }
    }

    private void notifyMonsterMoved(Monster monster, int newCell) {
        for (GameEventListener listener : listeners) {
            listener.onMonsterMoved(monster, newCell);
        }
    }

    private void notifyStatusChanged(Monster monster) {
        for (GameEventListener listener : listeners) {
            listener.onStatusEffectChanged(monster);
        }
    }
}