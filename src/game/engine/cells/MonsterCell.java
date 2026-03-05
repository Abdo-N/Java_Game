package game.engine.cells;

public class MonsterCell extends Cell{
	private final Monster cellMonster;
	public Monster getCellMonster() {
		return cellMonster;
	}
	public MonsterCell(String name, Monster cellMonster){
		super(name);
		this.cellMonster=cellMonster;
	}
}
