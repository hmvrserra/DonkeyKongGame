package pt.iscte.poo.game;

import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Room {
	private Manel manel;
	private List<GameObject> gameObjects;
	private char doorSymbol; // Símbolo da porta atual
	private String nextRoomFile; // Nome do próximo ficheiro de sala, se houver
	
	public Room() {
		this.gameObjects = new ArrayList<>();
		addBackground();
        loadRoomFromFile("rooms/roomTEST.txt");
		this.manel = new Manel(findStartingPositionForManel());
		gameObjects.add(manel);
        ImageGUI.getInstance().addImages(gameObjects);
    }
	
	private void loadRoomFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Ignorar a primeira linha
            }
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int x = 0; x < line.length(); x++) {
                    char ch = line.charAt(x);
                    Point2D position = new Point2D(x, y);
                    switch (ch) {
                        case 'W':
                            gameObjects.add(new Wall(position));
                            break;
                        case 'S':
                            gameObjects.add(new Stairs(position));
                            break;
                        case 'H':
                            gameObjects.add(new Hammer(position));
                            break;
                        case 'G':
                            gameObjects.add(new DonkeyKong(position));
                            break;
                        case 't':
                            gameObjects.add(new Trap(position));
                            break;
                        case 's':
                            gameObjects.add(new Sword(position));
                            break;
                        case '0':
                            gameObjects.add(new Door(position));
                            break;
                        //ADICIONAR O RESTO DOS OBJETOS
                    }
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
    }

	private Point2D findStartingPositionForManel() { // Encontra a posição mais abaixo e à esquerda possível para Manel
	    for (int y = 9; y >= 0; y--) {
	        for (int x = 0; x <= 9; x++) {
	            Point2D position = new Point2D(x, y);
				boolean isOccupied = false;
	            for (GameObject obj : gameObjects) {
	                if (isCollision(position)) {
	                    isOccupied = true;
						break;
	                }
	            }
				if (!isOccupied)
	            	return position;
	        }
	    }
	    return new Point2D(1, 1);
	}
	
	public void moveManel(Direction direction, Room room) {
		manel.move(direction, room);
	}

	public void addBackground() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				gameObjects.add(new Floor(new Point2D(x, y)));
			}
		}
	}

	public boolean isCollision(Point2D position) {
		for (GameObject obj : gameObjects) {
			if (obj instanceof Collidable && obj.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	public boolean isClimbable(Point2D position) {
		for (GameObject obj : gameObjects) {
			if (obj instanceof Climbable && obj.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	public void update() {
		manel.update(this);
	}
	public Manel getManel() {
		return this.manel;
	}

	@Override

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (GameObject gameObject : gameObjects) {
			sb.append(gameObject.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
