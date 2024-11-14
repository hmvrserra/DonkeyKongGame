package objects;

import pt.iscte.poo.utils.Point2D;

public abstract class MovableObject extends GameObject {
    public MovableObject(Point2D position, boolean isInteractable) {
        super(position, isInteractable);
    }

    // Método para mover o objeto (implementado nas subclasses móveis)
    public abstract void move();
}
