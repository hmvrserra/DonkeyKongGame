package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Door extends GameObject {
    private String destinationFile;
    private boolean isOpen;

    public Door(Point2D position, String destinationFile) {
        super(position, 1);
        this.destinationFile = destinationFile;
        this.isOpen = false;
    }

    @Override
    public String getName() {
        return isOpen ? "DoorOpen" : "DoorClosed";
    }

    public String getDestinationFile() {
        return destinationFile;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        this.isOpen = true;
    }

    public void interact() {
        if (!isOpen) {
            open();
            ImageGUI.getInstance().removeImage(this);
            ImageGUI.getInstance().addImage(this);
            ImageGUI.getInstance().update();
            ImageGUI.getInstance().setStatusMessage("Door opened!");
        } else {
            String destinationFile = getDestinationFile();
            GameEngine engine = GameEngine.getInstance();
            engine.changeRoom(destinationFile);
        }
    }
}
