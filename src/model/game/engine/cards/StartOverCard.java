package model.game.engine.cards;

import model.game.engine.Constants;
import model.game.engine.monsters.Monster;

public class StartOverCard extends Card {

	public StartOverCard(String name, String description, int rarity, boolean lucky) {
		super(name, description, rarity, lucky);
	}

	@Override
	public void performAction(Monster player, Monster opponent) {
		(this.isLucky() ? opponent : player).setPosition(Constants.STARTING_POSITION);
	}

}
