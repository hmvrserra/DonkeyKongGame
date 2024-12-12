package objects;

import pt.iscte.poo.utils.Point2D;

public class FireOld extends GameObject {
    public FireOld(Point2D position) {
        super(position, 1);
    }

    @Override
    public String getName() {
        return "Fire_old";
    }
}
