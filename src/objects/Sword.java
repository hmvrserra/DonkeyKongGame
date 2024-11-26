package objects;

import pt.iscte.poo.utils.Point2D;
public class Sword extends Item {

    public Sword(Point2D position) {
        super(position, 1);
    }
    
    @Override
    public String getName() {
        return "Sword";
    }
}
