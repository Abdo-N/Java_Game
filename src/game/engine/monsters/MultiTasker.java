package game.engine.monsters;

import game.engine.Role;

public class MultiTasker extends Monster {
	private int normalSpeedTurns;
	
	public MultiTasker(String name, String description, Role role, int energy){
		super(name, description, role, energy);
		this.normalSpeedTurns = 0;
	}
	//getter
	
	public int getNormalSpeedTurns(){
		return normalSpeedTurns;
	}
	
	//setter
	
	public void setNormalSpeedTurns(int normalSpeedTurns){
		if(normalSpeedTurns >= 0)
			this.normalSpeedTurns = normalSpeedTurns;
	}

}
