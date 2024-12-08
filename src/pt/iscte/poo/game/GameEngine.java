package pt.iscte.poo.game;

import objects.Manel;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Observer {

	public List<Room> rooms;
	private Room currentRoom;
	private int currentRoomNum = 0;
	private int lastTickProcessed = 0;
	private static GameEngine INSTANCE;
	
	public GameEngine() {
    	INSTANCE = this; 
        rooms = new ArrayList<>();
        loadAllRooms();
		sortRooms();
        currentRoom = rooms.get(0);
        ImageGUI.getInstance().addImages(currentRoom.getGameObjects());
        ImageGUI.getInstance().update();
    }
	
	public static GameEngine getActiveInstance() {
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
				currentRoom.moveManel(moveDirection, currentRoom);
			}
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			Point2D belowPosition = currentRoom.getManel().getPosition().plus(new Vector2D(0, 1));
			currentRoom.update();
			currentRoom.getDonkeyKong().throwBanana(currentRoom);
			processTick();
		}
		ImageGUI.getInstance().update();
		currentRoom.removeItem(currentRoom.getManel().getPosition());
	}

	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;

	}

	public void changeRoom(String destinationFile) {
        for (Room room : rooms) {
            File roomFile = new File(room.getFileName());
            if (roomFile.getName().equals(destinationFile)) {
                currentRoom = room;
                break;
            }
        }
        ImageGUI.getInstance().clearImages();
        ImageGUI.getInstance().addImages(currentRoom.getGameObjects());
        ImageGUI.getInstance().update();
    }

	private void sortRooms() {
		rooms.sort((r1, r2) -> r1.getFilename().compareTo(r2.getFilename()));
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}
}
