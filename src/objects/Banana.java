package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Banana extends GameObject {

    private Point2D currentPosition;

    public Banana(Point2D position) {
        super(position, 2);
        currentPosition = position;
    }

    @Override
    public String getName() {
        return "Banana";
    }

    public void fall(Room room) {
        Point2D newPosition = currentPosition.plus(new Vector2D(0, 1));

        setPosition(newPosition);

        currentPosition = newPosition;
    }

}
