package game.engine.cells;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class ContaminationSock extends TransportCell implements CanisterModifier{
	public ContaminationSock(String name, int effect){
		super(name,effect); //it says effect must be negative, do I throw an exception?
	}

	@Override
	public void modifyEnergy(Monster monster) {
		
	}
}
