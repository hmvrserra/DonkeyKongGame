package objects;

import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Manel extends Character {

	public Manel(Point2D position) {
		super(position, 2, 50, 5);
	}
	
	@Override
    public String getName() {
        return "JumpMan";
    }
	
	@Override
	public void update(Room room) {
	    super.update(room);

	    List<GameObject> objectsAtPosition = room.getObjectsAt(getPosition());	    
	    for (GameObject obj : objectsAtPosition) {
	        if (obj instanceof Door) {
	            ((Door) obj).interact();
	        }
	    }

		if (getHealth() <= 0) {
			ImageGUI.getInstance().setStatusMessage("JumpMan has died!");
		}
	}
	
}
