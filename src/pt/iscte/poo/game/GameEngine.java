package pt.iscte.poo.game;

import objects.Manel;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class GameEngine implements Observer {
	
	private Room currentRoom = new Room();
	private int lastTickProcessed = 0;
	
	public GameEngine() {
		ImageGUI.getInstance().update();
	}

	@Override
	public void update(Observed source) {
		
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
				Direction moveDirection = Direction.directionFor(k);
				System.out.println("User wants to move: " + moveDirection);
				currentRoom.moveManel(moveDirection, currentRoom);
			}
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			Point2D belowPosition = currentRoom.getManel().getPosition().plus(new Vector2D(0, -1));
			if (currentRoom.isClimbable(belowPosition)) {
				currentRoom.update();
			}
			processTick();
		}
		ImageGUI.getInstance().update();
	}

	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;

	}



}
