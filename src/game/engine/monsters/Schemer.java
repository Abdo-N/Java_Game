package game.engine.monsters;

import java.util.ArrayList;

import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	
	private int stealEnergyFrom(Monster target){
		int steal = Math.min(Constants.SCHEMER_STEAL, target.getEnergy());
	    target.setEnergy(target.getEnergy() - steal);
	    return steal;
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		int total = 0;
		total+=stealEnergyFrom(opponentMonster);
		ArrayList<Monster> m = new ArrayList<Monster>();
		for(int i = 0; i<m.size();i++){
			if(m.get(i)!=opponentMonster && m.get(i).getRole() == opponentMonster.getRole())
			total+=stealEnergyFrom(m.get(i));
		}
		this.setEnergy(this.getEnergy()+total);	
	}
	@Override
	public void setEnergy(int energy){
		super.setEnergy(energy+10);
	}
	
}
