package objects;

import pt.iscte.poo.utils.Point2D;
public class DonkeyKong extends Character {
    public DonkeyKong(Point2D position) {
        super(position, 2 , 100, 20);
    }
    
    @Override
    public String getName() {
        return "DonkeyKong";
    }
}
