package objects;

import pt.iscte.poo.utils.Point2D;

public class Door extends StaticObject {
    public Door(Point2D position) {
        super(position, true);
    }

    @Override
    public String getName() {
        return "DoorClosed";
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
