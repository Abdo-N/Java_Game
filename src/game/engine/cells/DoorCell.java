package game.engine.cells;

import game.engine.Board;
import game.engine.Role;
import game.engine.monsters.Monster;
import game.engine.interfaces.CanisterModifier;;

public class DoorCell extends Cell implements CanisterModifier{
	private Role role;
	private int energy;
	private boolean activated;
	
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public Role getRole() {
		return role;
	}
	public int getEnergy() {
		return energy;
	}
	
	public DoorCell(String name, Role role, int energy){
		super(name);
		this.role=role;
		this.energy=energy;
		activated=false;
		
	}
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {

	    super.onLand(landingMonster, opponentMonster);

	    if (isActivated())
	        return;

	    int energyBefore = landingMonster.getEnergy();
	    boolean wasShielded = landingMonster.isShielded();

	    int value = (landingMonster.getRole() == this.role) ? energy : -energy;

	    if (value < 0 && wasShielded) {
	        landingMonster.alterEnergy(value);
	        return;
	    }

	    modifyCanisterEnergy(landingMonster, energy);

	    for (Monster m : Board.getStationedMonsters()) {
	        if (m != landingMonster && m.getRole() == landingMonster.getRole()) {
	            modifyCanisterEnergy(m, energy);
	        }
	    }

	    boolean energyChanged = landingMonster.getEnergy() != energyBefore;
	    boolean shieldConsumed = wasShielded && !landingMonster.isShielded();

	    if (energyChanged || shieldConsumed) {
	        setActivated(true);
	    }
	}
        	   
  
	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {	
		if(!monster.getRole().equals(role)) canisterValue *= -1; 
		monster.alterEnergy(canisterValue);
	}
} 
