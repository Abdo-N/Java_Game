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
	
	public void onLand(Monster landingMonster, Monster opponentMonster) {
       super.onLand(landingMonster, opponentMonster);
        if (isActivated()== true ) { 
        	return ; 	
        }
        
        
        int canisterValue;
        if (landingMonster.getRole() == role)
            canisterValue = energy;
        else
            canisterValue = -energy;
        
        int energyBefore = landingMonster.getEnergy();
        
        modifyCanisterEnergy(landingMonster, canisterValue);
        	 
             
        int i = 0;
        while (i < Board.getStationedMonsters().size()) {
            if (Board.getStationedMonsters().get(i).getRole() == landingMonster.getRole())
                modifyCanisterEnergy(Board.getStationedMonsters().get(i), canisterValue);
            i++;
        }
        	   
   if (landingMonster.getEnergy() != energyBefore) {
        		  setActivated(true) ;  } }
        	   
        	   
           
           
        	
            	
	
	
       
	
	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		
	}
} 
