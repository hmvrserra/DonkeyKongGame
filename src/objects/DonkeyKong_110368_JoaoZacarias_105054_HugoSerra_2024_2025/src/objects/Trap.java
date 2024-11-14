package objects;

import pt.iscte.poo.utils.Point2D;

public class Trap extends StaticObject {
    public Trap(Point2D position) {
        super(position, true);
    }

    @Override
    public String getName() {
        return "Trap";
    }

    @Override
    public int getLayer() {
        return 1;
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }
    
    @Override
    public boolean isSupport() {
        return true;
    }
}
