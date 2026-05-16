package controller.interfaces;

import model.game.engine.Role;

public interface GameController {
    void handleStartGame(Role role);
    void handlePowerUp();
    void handleRollDice();
}
