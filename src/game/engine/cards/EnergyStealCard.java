// Represents energy steal cards. Subclass of Card and can modify energy.
package game.engine.cards;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class EnergyStealCard extends Card implements CanisterModifier{
	private int energy;
	
	 public EnergyStealCard(String name, String description, int rarity, int energy) {
		 super(name, description, rarity, true);
		 this.energy = energy;
		 		 
	 }

	 public int getEnergy() {
		 return energy;
	 }


	 @Override
	 public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		    monster.alterEnergy(canisterValue);
	 }

	 @Override
	 public void performAction(Monster player, Monster opponent) {
	     int energyBefore = opponent.getEnergy();
	     
	     // steal amount respecting opponent's available energy
	     int stealAmount = Math.min(this.energy, opponent.getEnergy());
	     
	     // modify opponent's canister (respects shield)
	     modifyCanisterEnergy(opponent, -stealAmount);
	     
	     // figure out what was actually stolen
	     int actualStolen = energyBefore - opponent.getEnergy();
	     
	     // give player the actual stolen amount
	     modifyCanisterEnergy(player, actualStolen);
	 }




}
