package game.engine;

import java.io.IOException;
import java.util.ArrayList;

import game.engine.dataloader.DataLoader;
import game.engine.monsters.Monster;

public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters;
	private Monster player;
	private Monster opponent;
	private Monster current;
	
	public Game(Role playerRole) throws IOException
	{
		board = new Board(DataLoader.readCards());
		allMonsters = DataLoader.readMonsters();
		
		Role opponentRole = (playerRole == Role.SCARER) ? Role.LAUGHER : Role.SCARER;
		opponent = selectRandomMonsterByRole(opponentRole);
		player = selectRandomMonsterByRole(playerRole);
		
		current = player;
	}
	
	private Monster selectRandomMonsterByRole(Role role) {
	    ArrayList<Monster> filtered = new ArrayList<>();
	    for (Monster m : allMonsters) {
	        if (m.getRole() == role) {
	            filtered.add(m);
	        }
	    }
	    int randomIndex = (int)(Math.random() * filtered.size());
	    return filtered.get(randomIndex);
	}
	
	public Monster getCurrent() {
		return current;
	}

	public void setCurrent(Monster current) {
		this.current = current;
	}

	public Board getBoard() {
		return board;
	}

	public ArrayList<Monster> getAllMonsters() {
		return allMonsters;
	}

	public Monster getPlayer() {
		return player;
	}

	public Monster getOpponent() {
		return opponent;
	}

	
}
