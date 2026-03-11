package game.engine.dataloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import game.engine.*;
import game.engine.cards.*;
import game.engine.cells.*;
import game.engine.monsters.*;

public class DataLoader {
	private static final String CARDS_FILE_NAME = "cards.csv";
	private static final String CELLS_FILE_NAME = "cells.csv";
	private static final String MONSTERS_FILE_NAME = "monsters.csv";
	
	public static ArrayList<Card> readCards() throws IOException
	{
		ArrayList<Card> cards = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(CARDS_FILE_NAME))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	Card newCard = null;
		        String[] values = line.split(",");
		        switch(values[0])
		        {
		        	case "SWAPPER": newCard = new SwapperCard(values[1], values[2], Integer.parseInt(values[3]));break;
		        	case "STARTOVER": newCard = new StartOverCard(values[1], values[2], Integer.parseInt(values[3]), Boolean.parseBoolean(values[4]));break;
		        	case "SHIELD": newCard = new ShieldCard(values[1], values[2], Integer.parseInt(values[3]));break;
		        	case "ENERGYSTEAL": newCard = new EnergyStealCard(values[1], values[2], Integer.parseInt(values[3]), Integer.parseInt(values[4]));break;
		        	case "CONFUSION": newCard = new ConfusionCard(values[1], values[2], Integer.parseInt(values[3]), Integer.parseInt(values[4]));break;
		        }
		        if(newCard != null)
		            cards.add(newCard);
		    }
		}
		return cards;
	}
	
	public static ArrayList<Cell> readCells() throws IOException
	{
		ArrayList<Cell> cells = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(CELLS_FILE_NAME))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	Cell newCell = null;
		        String[] values = line.split(",");
		        if(values.length == 3) //therefore it is DoorCell
		        {
		        	String name = values[0];
		        	Role role = Role.valueOf(values[1]);
		        	int energy = Integer.parseInt(values[2]);
		        	
		        	newCell = new DoorCell(name, role, energy);
		        } else if (values.length == 2) //therefore transportCell
		        {
		        	String name = values[0];
		        	int effect = Integer.parseInt(values[1]);
		        	
		        	if(effect < 0) //therefore contamination sock
		        	{
		        		newCell = new ContaminationSock(name,effect);
		        	} else //therefore conveyer belt
		        	{
		        		newCell = new ConveyorBelt(name,effect);
		        	}
		        }
		        if(newCell != null)
		            cells.add(newCell);
		    }
		}
		return cells;
	}
	
	public static ArrayList<Monster> readMonsters () throws IOException
	{
		ArrayList<Monster> monsters = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(MONSTERS_FILE_NAME))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	Monster newMonster = null;
		    	
		        String[] values = line.split(",");
		        
		        String monsterType = values[0];
		        String name = values[1];
		        String description = values[2];
		        Role role = Role.valueOf(values[3]);
		        int energy = Integer.parseInt(values[4]);
		        
		        switch(monsterType)
		        {
		        case "DASHER": newMonster = new Dasher(name, description, role, energy);break;
		        case "DYNAMO": newMonster = new Dynamo(name, description, role, energy);break;
		        case "MULTITASKER": newMonster = new MultiTasker(name, description, role, energy);break;
		        case "SCHEMER": newMonster = new Schemer(name, description, role, energy);break;
		        }
		        if(newMonster != null)
		            monsters.add(newMonster);
		    }
		}
		return monsters;
	}
	
	
}
