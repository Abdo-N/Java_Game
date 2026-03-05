package game.engine.cells;

abstract public class TransportCell extends Cell{
	int effect;
	
	TransportCell(String name, int effect){
		super(name);
		this.effect=effect;
	}
	public int getEffect() {
		return effect;
	}
}