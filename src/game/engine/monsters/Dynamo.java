package game.engine.monsters;

import game.engine.Role;

public class Dynamo extends Monster {
	
	public Dynamo(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	@Override
    public void setEnergy(int energy) {
        int change = energy - getEnergy();
        super.setEnergy(getEnergy() + change * 2);
    }

    @Override
    public void executePowerupEffect(Monster opponent) {
        opponent.setFrozen(true);
    }
	
}