package game.engine;

import game.engine.dataloader.DataLoader;
import game.engine.exceptions.*;
import game.engine.monsters.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {

    private Board board;
    private ArrayList<Monster> allMonsters;
    private Monster player;
    private Monster opponent;
    private Monster current;

    public Game(Role playerRole) throws IOException {
        this.board = new Board(DataLoader.readCards());
        this.allMonsters = DataLoader.readMonsters();
        this.player = selectRandomMonsterByRole(playerRole);
        this.opponent = selectRandomMonsterByRole(
            playerRole == Role.SCARER ? Role.LAUGHER : Role.SCARER
        );
        this.current = player;

        // 2.5.2 additions
        ArrayList<Monster> stationedMonsters = new ArrayList<>(allMonsters);
        stationedMonsters.remove(player);
        stationedMonsters.remove(opponent);
        board.setStationedMonsters(stationedMonsters);
        board.initializeBoard(DataLoader.readCells());
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

    public Monster getCurrent() {
        return current;
    }

    public void setCurrent(Monster current) {
        this.current = current;
    }

    private Monster selectRandomMonsterByRole(Role role) {
        Collections.shuffle(allMonsters);
        return allMonsters
            .stream()
            .filter(m -> m.getRole() == role)
            .findFirst()
            .orElse(null);
    }

    private int rollDice() {
        Random rd = new Random();
        return rd.nextInt(1, 7);
    }

    private Monster getCurrentOpponent() {
        if (current == player) return opponent;
        else return player;
    }

    private void switchTurn() {
        if (current == player) setCurrent(opponent);
        else setCurrent(player);
    }

    public void playTurn() throws InvalidMoveException {
        if (current.isFrozen()) {
            current.setFrozen(false);
            switchTurn();
            return;
        }
        int roll = rollDice();
        board.moveMonster(current, roll, getCurrentOpponent());
        switchTurn();
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

    public void usePowerup() throws OutOfEnergyException {
        if (current.getEnergy() >= Constants.POWERUP_COST) {
            current.alterEnergy(-Constants.POWERUP_COST);
            current.executePowerupEffect(getCurrentOpponent());
        } else throw new OutOfEnergyException();
    }

    private boolean checkWinCondition(Monster monster) {
        return (
            monster.getPosition() == Constants.BOARD_SIZE - 1 &&
            monster.getEnergy() >= Constants.WINNING_ENERGY
        );
    }

    public Monster getWinner() {
        if (checkWinCondition(player)) return player;
        if (checkWinCondition(opponent)) return opponent;
        return null;
    }
}
