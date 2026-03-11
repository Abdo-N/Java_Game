package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public abstract class Monster implements Comparable<Monster> {
	private String name; //read only
	private String description;  //read only
	private Role role;
	private Role originalRole;  //read only 
	private int energy;
	private int position;
	private boolean frozen;
	private boolean shielded;
	private int confusionTurns;
	
	public Monster(String name, String description, Role originalRole, int energy){
		this.name = name;
		this.description = description;
		this.originalRole = originalRole;
		this.energy = Math.max(0, energy);
		this.role = originalRole;
		this.position = 0;
		this.frozen = false;
		this.shielded = false;
		this.confusionTurns = 0;
	}
	
	//read only getters
	
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	public Role getOriginalRole(){
		return originalRole;
	}
	
	//read and write getters
	
	public Role getRole(){
		return role;
	}
	public int getEnergy(){
		return energy;
	}
	public int getPosition(){
		return position;
	}
	public boolean isFrozen(){
		return frozen;
	}
	public boolean isShielded(){
		return shielded;
	}
	public int getConfusionTurns(){
		return confusionTurns;
	}
	
	//read and write setters
	
	public void setRole(Role role){
		this.role = role;
	}
	public void setEnergy(int energy){
		this.energy = Math.max(0, energy); // negatives become 0
	}
	public void setPosition(int position){
		if(position >= 0)
	        this.position = position % Constants.BOARD_SIZE;
	}
	public void setFrozen(boolean frozen){
		this.frozen = frozen;
	}
	public void setShielded(boolean shielded){
		this.shielded = shielded;
	}
	public void setConfusionTurns(int confusionTurns){
		if(confusionTurns >= 0)
			this.confusionTurns = confusionTurns;
	}
	//@Override compareTo method
	
	public int compareTo(Monster other){
		return((this.position)-(other.position));
	}

}
