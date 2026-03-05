package game.engine.cells;

public class MonsterCell extends Cell{
	Monster cellMonster;
	public Monster getCellMonster() {
		return cellMonster;
	}
	MonsterCell(String name, Monster cellMonster){
		super(name);
		this.cellMonster=cellMonster;
	}
}
