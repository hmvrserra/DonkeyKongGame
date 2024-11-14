package objects;

import pt.iscte.poo.utils.Point2D;

public class GoodMeat extends StaticObject {
    public GoodMeat(Point2D position) {
        super(position, true);
    }

    @Override
    public String getName() {
        return "GoodMeat";
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
