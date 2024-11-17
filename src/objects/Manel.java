package objects;

import pt.iscte.poo.utils.Point2D;

public class Manel extends Character {
	
	private Point2D position;

	public Manel(Point2D position) {
		super(position, 2, 100, 20);
	}
	
	@Override
    public String getName() {
        return "JumpMan";
    }
}
