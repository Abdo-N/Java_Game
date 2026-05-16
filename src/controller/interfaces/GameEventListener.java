package controller.interfaces;

import model.game.engine.monsters.Monster;
import model.game.engine.cards.Card;

public interface GameEventListener {
    void onMonsterMoved(Monster monster, int newCell);
    void onEnergyChanged(Monster monster, int deltaEnergy);
    void onStatusEffectChanged(Monster monster);
    void onCardDrawn(Monster monster, Card card);
    void onTurnStarted(Monster current);
    void onTurnEnded(Monster current);
    void onMonsterFrozen(Monster monster);
    void onInvalidAction(String reason);
    void onGameWon(Monster winner);
    void onCellEffect(Monster monster, int oldCell, int newCell);
}
