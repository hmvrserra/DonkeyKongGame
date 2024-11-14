package objects;

import pt.iscte.poo.utils.Point2D;

public class Black extends StaticObject {
    public Black(Point2D position) {
        super(position, false);
    }

    @Override
    public String getName() {
        return "Black";
    }

    @Override
    public int getLayer() {
        return 0;
    }
}
