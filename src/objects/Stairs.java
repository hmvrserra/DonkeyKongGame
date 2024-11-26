package objects;

import pt.iscte.poo.utils.Point2D;
public class Stairs extends StaticObject{
    public Stairs(Point2D position) {
        super(position, 0);
    }
    
    @Override
    public String getName() {
        return "Stairs";
    }

    @Override
    public boolean canClimb() {
        return true;
    }

}
