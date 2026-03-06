// Represents confusion cards that confuse monsters of their role. Subclass of Card.
package game.engine.cards;

public class ConfusionCard extends Card {
	 private int duration;
	 
	 public ConfusionCard(String name, String description, int rarity, int duration) {
		 super(name, description, rarity, false);
		 
		 this.duration = duration;
	 }

	 public int getDuration() {
		 return duration;
	 }	 
}
