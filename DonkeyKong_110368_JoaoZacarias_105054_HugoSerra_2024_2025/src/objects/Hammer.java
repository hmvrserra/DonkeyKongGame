package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Hammer implements ImageTile {
    private Point2D position;

    public Hammer(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
}
