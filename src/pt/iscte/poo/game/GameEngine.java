package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameEngine implements Observer {

	public List<Room> rooms;
	private Room currentRoom;
	private int lastTickProcessed = 0;
	private static GameEngine INSTANCE;
	private int NUM_LIVES = 3;
	private long startTime;
	
	private int lastMeatTick = 0;
	private int lastBombTick = 0;
	private int lastFireTick = 0;
	
	private GameEngine() {
        rooms = new ArrayList<>();
        loadAllRooms();
		sortRooms();
        currentRoom = rooms.get(0);
        ImageGUI.getInstance().addImages(currentRoom.getGameObjects());
        ImageGUI.getInstance().update();
		
		startTime = System.currentTimeMillis();
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
			int kb = ImageGUI.getInstance().keyPressed();
			if (lastTickProcessed - lastMeatTick >= 15) {
				currentRoom.updateMeat();
				lastMeatTick = lastTickProcessed;
			}
			if (kb == 66) {
				currentRoom.getManel().throwBomb();
			}
			if (lastBombTick >= 15) {
				currentRoom.updateBomb();
			}
			if (lastFireTick >= 16) {
				currentRoom.updateFire();
				lastBombTick = 0;
				lastFireTick = 0;
			}
			currentRoom.update();
			processTick();
		}

		currentRoom.instantUpdate();
		ImageGUI.getInstance().update();

		if (currentRoom.getManel().getHealth() <= 0) {
			NUM_LIVES--;
			if (NUM_LIVES > 0) {
				ImageGUI.getInstance().setStatusMessage("You died! Lives left:" + NUM_LIVES);
				resetRoomState();
			}
			if (NUM_LIVES <= 0) {
				ImageGUI.getInstance().showMessage("Game Over", "You have no lives left!");
				System.exit(0);
			}
		}
		
		if (currentRoom.checkVictory()) {
			long completionTimeMillis = System.currentTimeMillis() - startTime;
			saveToLeaderboard(completionTimeMillis);
			ImageGUI.getInstance().showMessage("Congratulations!", "You have won the game!");
			displayLeaderboard();
			System.exit(0);
		}
	}

	private void saveToLeaderboard(long completionTimeMillis) {
		try {
			FileWriter writer = new FileWriter("leaderboard/leaderboard.txt", true);
			writer.write(completionTimeMillis + "\n");
			writer.close();
		} catch (IOException e) {
			System.err.println("Error writing to leaderboard: " + e.getMessage());
		}
	}

	private void displayLeaderboard() {
		StringBuilder leaderboardMessage = new StringBuilder("Leaderboard:\n");
		try {
			File file = new File("leaderboard/leaderboard.txt");
			Scanner scanner = new Scanner(file);

			List<Long> times = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				try {
					times.add(Long.parseLong(line));
				} catch (NumberFormatException e) {
					System.err.println("Invalid leaderboard entry: " + line);
				}
			}
			scanner.close();

			Collections.sort(times);
			for (int i = 0; i < Math.min(times.size(), 10); i++) {
				leaderboardMessage.append((i + 1)).append(". ").append(formatTime(times.get(i))).append("\n");
			}

			if (times.isEmpty()) {
				leaderboardMessage.append("No scores yet!");
			}

		} catch (FileNotFoundException e) {
			leaderboardMessage.append("Leaderboard file not found.");
		}

		ImageGUI.getInstance().showMessage("Leaderboard", leaderboardMessage.toString());
	}

	private String formatTime(long millis) {
		long seconds = millis / 1000;
		long minutes = seconds / 60;
		seconds = seconds % 60;
		return String.format(minutes + " min " + seconds + " sec");
	}

	private void resetRoomState() {
		lastMeatTick = 0;
		lastBombTick = 0;
		lastFireTick = 0;
		lastTickProcessed = ImageGUI.getInstance().getTicks();
		currentRoom.reset();
	}


	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;
		if (currentRoom.isBombThrown()){
			lastBombTick++;
			lastFireTick++;
		}
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
