package objects;

import pt.iscte.poo.utils.Point2D;

public class Hammer extends StaticObject {
    public Hammer(Point2D position) {
        super(position, true);
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
