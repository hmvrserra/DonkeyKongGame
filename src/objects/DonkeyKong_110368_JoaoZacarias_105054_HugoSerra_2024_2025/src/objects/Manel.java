package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends MovableObject {
    public Manel(Point2D position) {
        super(position, true); // Manel é interagível
    }

    @Override
    public String getName() {
        return "JumpMan";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void move() {
        setPosition(getPosition().plus(Direction.random().asVector()));
    }
}
