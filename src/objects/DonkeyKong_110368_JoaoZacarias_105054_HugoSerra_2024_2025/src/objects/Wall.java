package objects;

import pt.iscte.poo.utils.Point2D;

public class Wall extends StaticObject {
    public Wall(Point2D position) {
        super(position, false);
    }

    @Override
    public String getName() {
        return "Wall";
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
