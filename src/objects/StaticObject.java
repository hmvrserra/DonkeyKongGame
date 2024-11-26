package objects;

import pt.iscte.poo.utils.Point2D;
public abstract class StaticObject extends GameObject{
    public StaticObject(Point2D position, int layer) {
        super(position, layer);
    }

    public boolean canClimb() {
        return false;
    }
}


