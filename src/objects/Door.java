package objects;

import pt.iscte.poo.utils.Point2D;
public class Door extends GameObject implements Interactable{

    public Door(Point2D position) {
        super(position, 1);
    }

    @Override
    public String getName() {
        return "DoorClosed";
    }

    @Override
    public boolean isInteractable(){
        return true;
    }

}
