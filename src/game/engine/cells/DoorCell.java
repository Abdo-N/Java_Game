package game.engine.cells;

import game.engine.Role;
import game.engine.monsters.Monster;
import game.engine.interfaces.CanisterModifier;;

public class DoorCell extends Cell implements CanisterModifier{
	private final Role role;
	private final int energy;
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
	
	public void modifyEnergy(Monster monster) {
		
	}
}
