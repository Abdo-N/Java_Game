//Represents shield cards that protect monsters from energy losing. Subclass of Card.
package game.engine.cards;

public class ShieldCard extends Card {
	
	public ShieldCard(String name, String description, int rarity) {
		 super(name, description, rarity, true);
	}

}
