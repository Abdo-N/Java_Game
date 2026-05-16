package model.game.engine.cells;

import controller.interfaces.CanisterModifier;
import model.game.engine.Constants;
import model.game.engine.monsters.Monster;

public class ContaminationSock extends TransportCell implements CanisterModifier {
	
	public ContaminationSock(String name, int effect) {
		super(name, effect);
	}

	@Override
    public void transport(Monster monster) {
		super.transport(monster);
        
        // Apply slip penalty
		modifyCanisterEnergy(monster, -Constants.SLIP_PENALTY);
    }

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue);
	}

}