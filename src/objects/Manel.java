package objects;

import java.util.List;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Manel extends Character{
	
	private int bombNum = 0;
	
	public Manel(Point2D position) {
		super(position, 3, 50, 5);
	}
	
	@Override
    public String getName() {
        return "JumpMan";
    }
	
	@Override
	public void update() {
	    super.update();

		Room room = GameEngine.getInstance().getCurrentRoom();

	    List<GameObject> objectsAtPosition = room.getObjectsAt(getPosition());	    
	    for (GameObject obj : objectsAtPosition) {
	        if (obj instanceof Door) {
	            ((Door) obj).interact();
	        }
	    }
	}
	public boolean hasBombs(){
		return bombNum > 0;
	}
	
	public void collectBomb(){
		bombNum++;
	}

	public void useBomb(){
		bombNum--;
	}
	
	public void throwBomb(){
		GameEngine gameEngine = GameEngine.getInstance();
		Room room = GameEngine.getInstance().getCurrentRoom();
		int k = ImageGUI.getInstance().keyPressed();
		
		if (hasBombs()) {
			ExplodingBomb b = new ExplodingBomb(getPosition());
			room.addGameObject(b);
			room.setBombThrown(true);
			useBomb();
		}
	}
}
