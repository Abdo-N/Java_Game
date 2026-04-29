package game.engine.cells;

import game.engine.Role;
import game.engine.monsters.Monster;


public class Cell {
	private String name;
	private Monster monster;
	
	public Cell(String name) {
		this.name = name;
		this.monster = null;
	}
	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public String getName() {
		return name;
	}
	 
	public boolean isOccupied()  {
		if( monster != null) return true ;
		else { return false; }
		}
	
	
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		setMonster(landingMonster) ;
		
		
		
	}
	}
