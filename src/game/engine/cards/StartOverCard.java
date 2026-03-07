//Represents start over cards that resets position. Subclass of Card. Can be lucky or
//unlucky.
package game.engine.cards;

public class StartOverCard extends Card{
	
	public StartOverCard(String name, String description, int rarity, boolean lucky) {
		super(name, description, rarity, lucky);	
		}

}
