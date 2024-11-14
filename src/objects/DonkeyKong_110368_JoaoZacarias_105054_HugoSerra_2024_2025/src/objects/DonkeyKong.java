package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends MovableObject {
    public DonkeyKong(Point2D position) {
    	super(position, true);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public int getLayer() {
        return 1;
    }
    
    @Override
    public void move() {}

}