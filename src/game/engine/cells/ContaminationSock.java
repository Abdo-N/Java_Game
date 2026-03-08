package game.engine.cells;

import game.engine.Role;
import game.engine.monsters.Monster;
import game.engine.interfaces.CanisterModifier;;

public class ContaminationSock extends TransportCell implements CanisterModifier{
	public ContaminationSock(String name, int effect){
		super(name,effect); //it says effect must be negative, do I throw an exception?
	}

	@Override
	public void modifyEnergy(Monster monster) {
		
	}
}
