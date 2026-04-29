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
	 int energyBefore = (opponent.getEnergy());
	 //find steal amount and checks if opponent has less than initial stolen
     int stealAmount =   Math.min(this.energy,opponent.getEnergy());
     //steal amount from opponent and respect shield
     opponent.alterEnergy(-stealAmount);
     //figure amount actually stolen 
     int actualStolen = energyBefore - opponent.getEnergy();
     //add energy to player
     player.alterEnergy(+actualStolen);
     //modify both their energies
     modifyCanisterEnergy(player, actualStolen);
     modifyCanisterEnergy(opponent, -actualStolen);
      
	 }




}
