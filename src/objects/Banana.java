package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Banana extends Item implements Collectable {

    public Banana(Point2D position) {
        super(position, 2);
    }

    @Override
    public String getName() {
        return "Banana";
    }
}
