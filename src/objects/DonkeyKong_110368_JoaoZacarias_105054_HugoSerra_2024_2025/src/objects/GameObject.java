package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameObject implements ImageTile {
    private Point2D position;
    private boolean isInteractable;

    public GameObject(Point2D position, boolean isInteractable) {
        this.position = position;
        this.isInteractable = isInteractable;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public boolean isInteractable() {
        return isInteractable;
    }
    
    public boolean isPassable() {
        return true;
    }
    
    public boolean isClimbable() {
        return false;
    }
    
    public boolean isSupport() {
        return false;
    }

    public abstract String getName();

    public abstract int getLayer();
}
