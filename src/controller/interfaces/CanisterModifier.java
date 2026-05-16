package controller.interfaces;

import model.game.engine.monsters.Monster;

public interface CanisterModifier {
	void modifyCanisterEnergy(Monster monster, int canisterValue);
}
