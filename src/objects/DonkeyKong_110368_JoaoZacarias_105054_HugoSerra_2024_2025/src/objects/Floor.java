package objects;

import pt.iscte.poo.utils.Point2D;

public class Floor extends StaticObject {
    public Floor(Point2D position) {
        super(position, false);
    }

    @Override
    public String getName() {
        return "Floor";
    }

    @Override
    public int getLayer() {
        return 1;
    }
    
    @Override
    public boolean isSupport() {
        return true;
    }
}
