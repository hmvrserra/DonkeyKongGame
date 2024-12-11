package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

import javax.print.attribute.standard.PresentationDirection;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Observer {

	public List<Room> rooms;
	private Room currentRoom;
	private int lastTickProcessed = 0;
	private static GameEngine INSTANCE;
	private int NUM_LIVES = 3;
	
	private GameEngine() {
        rooms = new ArrayList<>();
        loadAllRooms();
		sortRooms();
        currentRoom = rooms.get(0);
        ImageGUI.getInstance().addImages(currentRoom.getGameObjects());
        ImageGUI.getInstance().update();
    }
	
	public static GameEngine getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GameEngine();
		}
		return INSTANCE;
    }

    private void loadAllRooms() {
        File folder = new File("rooms");
        File[] files = folder.listFiles();
        for (File file : files) {
			if (file.isFile()){
				Room room = new Room(file.getPath());
				rooms.add(room);
			}
		}
    }
	
	@Override
	public void update(Observed source) {

		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
				Direction moveDirection = Direction.directionFor(k);
				System.out.println("User wants to move: " + moveDirection);
				currentRoom.moveManel(moveDirection);
			}
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			currentRoom.update();
			processTick();
			System.out.println("Manel Damage: " + currentRoom.getManel().getDamage());
		}

		currentRoom.instantUpdate();
		ImageGUI.getInstance().update();

		if (currentRoom.getManel().getHealth() <= 0) {
			NUM_LIVES--;
			if (NUM_LIVES > 0) {
				ImageGUI.getInstance().setStatusMessage("You died! Lives left:" + NUM_LIVES);
				currentRoom.reset();
			}
			if (NUM_LIVES <= 0) {
				ImageGUI.getInstance().showMessage("Game Over", "You have no lives left!");
				System.exit(0);
			}
		}
	}

	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;
	}

	public void changeRoom(String destinationFile) {
        for (Room room : rooms) {
            File roomFile = new File(room.getFilename());
            if (roomFile.getName().equals(destinationFile)) {
                currentRoom = room;
                break;
            }
        }
        ImageGUI.getInstance().clearImages();
        ImageGUI.getInstance().addImages(currentRoom.getGameObjects());
        ImageGUI.getInstance().update();
    }

	public Room getCurrentRoom() {
		return currentRoom;
	}

	private void sortRooms() {
		rooms.sort((r1, r2) -> r1.getFilename().compareTo(r2.getFilename()));
	}
}
