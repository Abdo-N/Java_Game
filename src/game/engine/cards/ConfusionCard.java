// Represents confusion cards that confuse monsters of their role. Subclass of Card.
package game.engine.cards;

import game.engine.Role;
import game.engine.monsters.Monster;

public class ConfusionCard extends Card {
	 private int duration;
	 
	 public ConfusionCard(String name, String description, int rarity, int duration) {
		 super(name, description, rarity, false);
		 
		 this.duration = duration;
	 }

	 public int getDuration() {
		 return duration;
	 }

	 @Override
	 public void performAction(Monster player, Monster opponent) {
		
		 Role temp = player.getRole();
		 player.setRole(opponent.getRole());
		 opponent.setRole(temp);
		 player.setConfusionTurns(duration);
		 opponent.setConfusionTurns(duration);
	 	 
		
	 }	 
}
