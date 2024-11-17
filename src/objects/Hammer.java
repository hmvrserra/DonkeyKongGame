package objects;

import pt.iscte.poo.utils.Point2D;

public class Hammer extends Item {
    public Hammer(Point2D position) {
        super(position, 1);
    }

    @Override
    public String getName() {
        return "Hammer";
    }
}
