package objects;

import pt.iscte.poo.utils.Point2D;

public class Floor extends GameObject{

	public Floor(Point2D position) {
		super(position, 0);
	}
	
	@Override
    public String getName() {
        return "Floor";
    }

}
