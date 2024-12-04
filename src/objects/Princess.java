package objects;

import pt.iscte.poo.utils.Point2D;

public class Princess extends GameObject implements Collidable{

	public Princess(Point2D position) {
		super(position, 1);
	}
	
	@Override
    public String getName() {
        return "Princess";
    }
}
