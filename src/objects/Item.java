package objects;

import pt.iscte.poo.utils.Point2D;
public abstract class Item extends GameObject implements Collectable {
    public Item(Point2D position, int layer) {
        super(position, layer);
    }

    @Override
    public boolean isCollectable() {
        return true;
    }
}
