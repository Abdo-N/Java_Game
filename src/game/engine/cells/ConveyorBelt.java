package game.engine.cells;

import game.engine.Role;
import game.engine.monsters.Monster;

public class ConveyorBelt extends TransportCell{
	public ConveyorBelt(String name, int effect){
		super(name,effect);//it says effect must be positive, do I throw an exception?
	}
}
