package objects;

import pt.iscte.poo.utils.Point2D;
public class Door extends StaticObject {

    public Door(Point2D position) {
        super(position, 1);
    }
    
    @Override
    public String getName() {
        return "DoorClosed";
    }

    //PORTA AINDA SEM OS DIFERENTES ESTADOS
}
