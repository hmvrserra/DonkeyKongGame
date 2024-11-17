package objects;

import pt.iscte.poo.utils.Point2D;
public class Stairs extends StaticObject implements Climbable {
    public Stairs(Point2D position) {
        super(position, 0);
    }
    
    @Override
    public String getName() {
        return "Stairs";
    }
}
