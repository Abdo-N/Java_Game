package game.engine.cells;

import game.engine.Constants;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class ContaminationSock extends TransportCell implements CanisterModifier {

	public ContaminationSock(String name, int effect) {
		super(name, effect);
	}
	
	public void transport(Monster monster){
		monster.move(-getEffect());
		monster.alterEnergy(Constants.SLIP_PENALTY);
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		// TODO Auto-generated method stub
		
	}
	

}

