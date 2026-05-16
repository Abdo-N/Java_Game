package model.game.engine.monsters;

import model.game.engine.Role;

public class Dynamo extends Monster {
	
	public Dynamo(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	
	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		opponentMonster.setFrozen(true);
	}
	
	@Override
	public void setEnergy(int energy) {
		super.setEnergy(getEnergy() + (energy - getEnergy()) * 2);
	}
}
