package objects;

import pt.iscte.poo.utils.Point2D;

public class Wall extends StaticObject implements Collidable{

	public Wall(Point2D position) {
		super(position, 1);
	}
	
	@Override
    public String getName() {
        return "Wall";
    }
}
