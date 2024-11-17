package objects;

import pt.iscte.poo.utils.Point2D;
public class Trap extends StaticObject {

    public Trap(Point2D position) {
        super(position, 0);
    }
    
    @Override
    public String getName() {
        return "Trap";
    }
}
