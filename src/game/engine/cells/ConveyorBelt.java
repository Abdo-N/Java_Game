package game.engine.cells;

public class ConveyorBelt extends TransportCell{
	ConveyorBelt(String name, int effect){
		super(name,effect);//it says effect must be positive, do I throw an exception?
	}
}
