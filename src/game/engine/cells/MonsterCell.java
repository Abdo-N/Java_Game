package game.engine.cells;

import game.engine.monsters.*;

public class MonsterCell extends Cell {
	private Monster cellMonster;

	public MonsterCell(String name, Monster cellMonster) {
		super(name);
		this.cellMonster = cellMonster;
	}

	public Monster getCellMonster() {
		return cellMonster;
	}
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
	    super.onLand(landingMonster, opponentMonster);
	    
	    if (cellMonster.getRole() == landingMonster.getRole()) {
	        // Same role → free powerup
	        landingMonster.executePowerupEffect(opponentMonster);
	    } else {
	        // Different role → swap energy if landing monster has more
	        if (landingMonster.getEnergy() > cellMonster.getEnergy()) {
	            int landingEnergy = landingMonster.getEnergy();
	            int cellEnergy = cellMonster.getEnergy();
	            
	            cellMonster.setEnergy(landingEnergy);      // unconditional
	            landingMonster.alterEnergy(cellEnergy - landingEnergy); // respects shield
	        }
	    }
	}

}
