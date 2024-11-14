package objects;

import pt.iscte.poo.utils.Point2D;

public class Stairs extends StaticObject {
    public Stairs(Point2D position) {
        super(position, false);
    }

    @Override
    public String getName() {
        return "Stairs";
    }

    @Override
    public int getLayer() {
        return 1;
    }
    
    @Override
    public boolean isClimbable() {
        return true;
    }
    
    @Override
    public boolean isSupport() {
        return true;
    }
}
