package game.engine.cells;

import game.engine.Role;
import game.engine.monsters.Monster;

abstract public class TransportCell extends Cell{
	private final int effect;
	
	public TransportCell(String name, int effect){
		super(name);
		this.effect=effect;
	}
	public int getEffect() {
		return effect;
	}
}