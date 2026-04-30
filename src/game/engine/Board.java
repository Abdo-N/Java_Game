package game.engine;

import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.exceptions.*;
import game.engine.monsters.Monster;
import java.util.ArrayList;

public class Board {

    private Cell[][] boardCells;
    private static ArrayList<Monster> stationedMonsters;
    private static ArrayList<Card> originalCards;
    public static ArrayList<Card> cards;

    public Board(ArrayList<Card> readCards) {
        this.boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
        stationedMonsters = new ArrayList<Monster>();
        originalCards = readCards;
        cards = new ArrayList<Card>();
        setCardsByRarity();
        reloadCards();
    }

    public Cell[][] getBoardCells() {
        return boardCells;
    }

    public static ArrayList<Monster> getStationedMonsters() {
        return stationedMonsters;
    }

    public static void setStationedMonsters(
        ArrayList<Monster> stationedMonsters
    ) {
        Board.stationedMonsters = stationedMonsters;
    }

    public static ArrayList<Card> getOriginalCards() {
        return originalCards;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public static void setCards(ArrayList<Card> cards) {
        Board.cards = cards;
    }

    private int[] indexToRowCol(int index) {
        int row = index / Constants.BOARD_COLS;
        int col = index % Constants.BOARD_COLS;
        if (row % 2 != 0) col = Constants.BOARD_COLS - 1 - col;

        return new int[] { row, col };
    }

    private Cell getCell(int index) {
        int[] position = indexToRowCol(index);
        int row = position[0];
        int col = position[1];

        return boardCells[row][col];
    }

    private void setCell(int index, Cell cell) {
        int[] position = indexToRowCol(index);
        int row = position[0];
        int col = position[1];

        boardCells[row][col] = cell;
    }

    public void initializeBoard(ArrayList<Cell> specialCells) {
    	ArrayList<Cell> doorCells = new ArrayList<>();
    	ArrayList<Cell> conveyorCells = new ArrayList<>();
    	ArrayList<Cell> sockCells = new ArrayList<>();
    	for (Cell c : specialCells) {
    	    if (c instanceof DoorCell) doorCells.add(c);
    	    else if (c instanceof ConveyorBelt) conveyorCells.add(c);
    	    else if (c instanceof ContaminationSock) sockCells.add(c);
    	}

    	int doorIndex = 0, conveyorIndex = 0, sockIndex = 0, monsterIndex = 0;

    	for (int i = 0; i < Constants.BOARD_ROWS * Constants.BOARD_COLS; i++) {
    	    if (contains(Constants.MONSTER_CELL_INDICES, i)) {
    	        if (monsterIndex < stationedMonsters.size()) {
    	            Monster m = stationedMonsters.get(monsterIndex);
    	            m.setPosition(i);
    	            setCell(i, new MonsterCell(m.getName(), m));
    	            monsterIndex++;
    	        } else {
    	            setCell(i, new MonsterCell("Empty", null));
    	        }
    	    } else if (contains(Constants.CARD_CELL_INDICES, i)) {
    	        setCell(i, new CardCell("CardCell"));
    	    } else if (contains(Constants.CONVEYOR_CELL_INDICES, i)) {
    	        setCell(i, conveyorCells.get(conveyorIndex++));
    	    } else if (contains(Constants.SOCK_CELL_INDICES, i)) {
    	        setCell(i, sockCells.get(sockIndex++));
    	    } else if (i % 2 == 0) {
    	        setCell(i, new Cell("Rest"));
    	    } else {
    	        if (doorIndex < doorCells.size())
    	            setCell(i, doorCells.get(doorIndex++));
    	    }
    	}
    }

    private boolean isSpecialIndex(int index) {
        return (
            contains(Constants.MONSTER_CELL_INDICES, index) ||
            contains(Constants.CONVEYOR_CELL_INDICES, index) ||
            contains(Constants.SOCK_CELL_INDICES, index) ||
            contains(Constants.CARD_CELL_INDICES, index)
        );
    }

    private boolean contains(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return true;
            }
        }
        return false;
    }

    private void setCardsByRarity() {
        ArrayList<Card> expandedCards = new ArrayList<>();
        for (int i = 0; i < originalCards.size(); i++) {
            Card c = originalCards.get(i);
            int rarity = c.getRarity();
            for (int j = 0; j < rarity; j++) {
                expandedCards.add(c);
            }
        }
        originalCards = expandedCards; // update originalCards, not cards
    }

    public static void reloadCards() {
        ArrayList<Card> newDeck = new ArrayList<>(originalCards);
        java.util.Collections.shuffle(newDeck);
        cards = newDeck;
    }

    public static Card drawCard() {
        if (cards.isEmpty()) reloadCards();
        return cards.remove(0);
    }

    private void updateMonsterPositions(Monster player, Monster opponent) {
        for (int i = 0; i < Constants.BOARD_ROWS * Constants.BOARD_COLS; i++) {
            Cell cell = getCell(i);
            cell.setMonster(null);
        }
        int playerIndex = player.getPosition();
        Cell playerCell = getCell(playerIndex);
        playerCell.setMonster(player);

        int opponentIndex = opponent.getPosition();
        Cell opponentCell = getCell(opponentIndex);
        opponentCell.setMonster(opponent);
    }

    public void moveMonster(
        Monster currentMonster,
        int roll,
        Monster opponentMonster
    ) throws InvalidMoveException {
        int oldPosition = currentMonster.getPosition();

        currentMonster.move(roll);

        Cell landedCell = getCell(currentMonster.getPosition());
        landedCell.onLand(currentMonster, opponentMonster);

        if (currentMonster.getPosition() == opponentMonster.getPosition()) {
            currentMonster.setPosition(oldPosition);
            throw new InvalidMoveException();
        }

        if (currentMonster.isConfused() && opponentMonster.isConfused()) {
            currentMonster.decrementConfusion();
            opponentMonster.decrementConfusion();
        }

        updateMonsterPositions(currentMonster, opponentMonster);
    }
}
